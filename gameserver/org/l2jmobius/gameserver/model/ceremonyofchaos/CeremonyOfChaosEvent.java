package org.l2jmobius.gameserver.model.ceremonyofchaos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import util.Chronos;
import util.Rnd;
import org.l2jmobius.gameserver.enums.CeremonyOfChaosResult;
import org.l2jmobius.gameserver.instancemanager.CeremonyOfChaosManager;
import org.l2jmobius.gameserver.instancemanager.GlobalVariablesManager;
import org.l2jmobius.gameserver.instancemanager.InstanceManager;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.Party.MessageType;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.appearance.PlayerAppearance;
import org.l2jmobius.gameserver.model.actor.instance.MonsterInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.eventengine.AbstractEvent;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.ListenerRegisterType;
import org.l2jmobius.gameserver.model.events.annotations.RegisterEvent;
import org.l2jmobius.gameserver.model.events.annotations.RegisterType;
import org.l2jmobius.gameserver.model.events.impl.ceremonyofchaos.OnCeremonyOfChaosMatchResult;
import org.l2jmobius.gameserver.model.events.impl.creature.OnCreatureDeath;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerLogout;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.holders.SkillHolder;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.DeleteObject;
import org.l2jmobius.gameserver.network.serverpackets.ExUserInfoAbnormalVisualEffect;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;
import org.l2jmobius.gameserver.network.serverpackets.SkillCoolTime;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.network.serverpackets.appearance.ExCuriousHouseMemberUpdate;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseEnter;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseLeave;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseMemberList;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseObserveMode;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseRemainTime;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseResult;

/**
 * @author UnAfraid
 */
public class CeremonyOfChaosEvent extends AbstractEvent<CeremonyOfChaosMember>
{
	private static final Logger LOGGER = Logger.getLogger(CeremonyOfChaosEvent.class.getName());
	
	private final int _id;
	private final Instance _instance;
	private final Set<MonsterInstance> _monsters = ConcurrentHashMap.newKeySet();
	private long _battleStartTime = 0;
	
	public CeremonyOfChaosEvent(int id, InstanceTemplate template)
	{
		_id = id;
		_instance = InstanceManager.getInstance().createInstance(template, null);
		if (_instance.getEnterLocations().size() < CeremonyOfChaosManager.getInstance().getMaxPlayersInArena())
		{
			LOGGER.warning("There are more member slots: " + _instance.getEnterLocations().size() + " then instance entrance positions: " + CeremonyOfChaosManager.getInstance().getMaxPlayersInArena() + "!");
		}
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getInstanceId()
	{
		return _instance.getId();
	}
	
	public Instance getInstance()
	{
		return _instance;
	}
	
	public Set<MonsterInstance> getMonsters()
	{
		return _monsters;
	}
	
	public void preparePlayers()
	{
		final Map<Integer, CeremonyOfChaosMember> members = getMembers();
		final ExCuriousHouseMemberList membersList = new ExCuriousHouseMemberList(_id, CeremonyOfChaosManager.getInstance().getMaxPlayersInArena(), members.values());
		final NpcHtmlMessage msg = new NpcHtmlMessage(0);
		int index = 0;
		for (CeremonyOfChaosMember member : members.values())
		{
			final PlayerInstance player = member.getPlayer();
			if (player.inObserverMode())
			{
				player.leaveObserverMode();
			}
			
			if (player.isInDuel())
			{
				player.setInDuel(0);
			}
			
			// Remember player's last location
			player.setLastLocation();
			
			// Hide player information
			final PlayerAppearance app = player.getAppearance();
			app.setVisibleName("Challenger" + member.getPosition());
			app.setVisibleTitle("");
			app.setVisibleClanData(0, 0, 0, 0, 0);
			
			// Register the event instance
			player.registerOnEvent(this);
			
			// Load the html
			msg.setFile(player, "data/html/CeremonyOfChaos/started.htm");
			
			// Remove buffs
			player.stopAllEffectsExceptThoseThatLastThroughDeath();
			
			// Player shouldn't be able to move and is hidden
			player.setImmobilized(true);
			player.setInvisible(true);
			
			// Same goes for summon
			player.getServitors().values().forEach(s ->
			{
				s.stopAllEffectsExceptThoseThatLastThroughDeath();
				s.setInvisible(true);
				s.setImmobilized(true);
			});
			
			if (player.isFlyingMounted())
			{
				player.untransform();
			}
			
			// If player is dead, revive it
			if (player.isDead())
			{
				player.doRevive();
			}
			
			// If player is sitting, stand up
			if (player.isSitting())
			{
				player.standUp();
			}
			
			// If player in party, leave it
			final Party party = player.getParty();
			if (party != null)
			{
				party.removePartyMember(player, MessageType.EXPELLED);
			}
			
			// Cancel any started action
			player.abortAttack();
			player.abortCast();
			player.stopMove(null);
			player.setTarget(null);
			
			// Unsummon pet
			final Summon pet = player.getPet();
			if (pet != null)
			{
				pet.unSummon(player);
			}
			
			// Unsummon agathion
			if (player.getAgathionId() > 0)
			{
				player.setAgathionId(0);
			}
			
			// The character???s HP, MP, and CP are fully recovered.
			player.setCurrentHp(player.getMaxHp());
			player.setCurrentMp(player.getMaxMp());
			player.setCurrentCp(player.getMaxCp());
			
			// Skill reuse timers for all skills that have less than 15 minutes of cooldown time are reset.
			for (Skill skill : player.getAllSkills())
			{
				if (skill.getReuseDelay() <= 900000)
				{
					player.enableSkill(skill);
				}
			}
			
			player.sendSkillList();
			player.sendPacket(new SkillCoolTime(player));
			
			// Apply the Energy of Chaos skill
			for (SkillHolder holder : CeremonyOfChaosManager.getInstance().getVariables().getList(CeremonyOfChaosManager.INITIAL_BUFF_KEY, SkillHolder.class))
			{
				holder.getSkill().activateSkill(player, player);
			}
			
			// Send Enter packet
			player.sendPacket(ExCuriousHouseEnter.STATIC_PACKET);
			
			// Send all members
			player.sendPacket(membersList);
			
			// Send the entrance html
			player.sendPacket(msg);
			
			// Send support items to player
			for (ItemHolder holder : CeremonyOfChaosManager.getInstance().getRewards(CeremonyOfChaosManager.INITIAL_ITEMS_KEY).calculateDrops())
			{
				player.addItem("CoC", holder, null, true);
			}
			
			// Teleport player to the arena
			player.teleToLocation(_instance.getEnterLocations().get(index++), 0, _instance);
		}
		
		final StatSet params = new StatSet();
		params.set("time", 60);
		getTimers().addTimer("match_start_countdown", params, 100, null, null);
		getTimers().addTimer("teleport_message1", 10000, null, null);
		getTimers().addTimer("teleport_message2", 14000, null, null);
		getTimers().addTimer("teleport_message3", 18000, null, null);
	}
	
	public void startFight()
	{
		for (CeremonyOfChaosMember member : getMembers().values())
		{
			final PlayerInstance player = member.getPlayer();
			if (player != null)
			{
				player.sendPacket(SystemMessageId.THE_MATCH_HAS_STARTED_FIGHT);
				player.setImmobilized(false);
				player.setInvisible(false);
				player.broadcastInfo();
				player.sendPacket(new ExUserInfoAbnormalVisualEffect(player));
				player.getServitors().values().forEach(s ->
				{
					s.setInvisible(false);
					s.setImmobilized(false);
					s.broadcastInfo();
				});
			}
		}
		_battleStartTime = Chronos.currentTimeMillis();
		getTimers().addRepeatingTimer("update", 1000, null, null);
	}
	
	public void stopFight()
	{
		final Map<Integer, CeremonyOfChaosMember> members = getMembers();
		for (CeremonyOfChaosMember member : members.values())
		{
			if (member.getLifeTime() == 0)
			{
				updateLifeTime(member);
			}
		}
		validateWinner();
		
		final List<CeremonyOfChaosMember> winners = getWinners();
		final List<CeremonyOfChaosMember> memberList = new ArrayList<>(members.size());
		SystemMessage msg = null;
		if (winners.isEmpty() || (winners.size() > 1))
		{
			msg = new SystemMessage(SystemMessageId.THERE_IS_NO_VICTOR_THE_MATCH_ENDS_IN_A_TIE);
		}
		else
		{
			final PlayerInstance winner = winners.get(0).getPlayer();
			if (winner != null)
			{
				msg = new SystemMessage(SystemMessageId.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH);
				msg.addString(winner.getName());
				
				// Rewards according to https://l2wiki.com/Ceremony_of_Chaos
				final int marksRewarded = Rnd.get(2, 4);
				winner.addItem("CoC-Winner", 34900, marksRewarded, winner, true); // Mysterious Marks
				
				// Possible additional rewards
				
				// Improved Life Stone
				if (Rnd.get(10) < 3) // Chance to get reward (30%)
				{
					switch (Rnd.get(4))
					{
						case 0:
						{
							winner.addItem("CoC-Winner", 18570, 1, winner, true); // Improved Life Stone (R95-grade)
							break;
						}
						case 1:
						{
							winner.addItem("CoC-Winner", 18571, 1, winner, true); // Improved Life Stone (R95-grade)
							break;
						}
						case 2:
						{
							winner.addItem("CoC-Winner", 18575, 1, winner, true); // Improved Life Stone (R99-grade)
							break;
						}
						case 3:
						{
							winner.addItem("CoC-Winner", 18576, 1, winner, true); // Improved Life Stone (R99-grade)
							break;
						}
					}
				}
				// Soul Crystal Fragment
				else if (Rnd.get(10) < 3) // Chance to get reward (30%)
				{
					switch (Rnd.get(6))
					{
						case 0:
						{
							winner.addItem("CoC-Winner", 19467, 1, winner, true); // Yellow Soul Crystal Fragment (R99-Grade)
							break;
						}
						case 1:
						{
							winner.addItem("CoC-Winner", 19468, 1, winner, true); // Teal Soul Crystal Fragment (R99-Grade)
							break;
						}
						case 2:
						{
							winner.addItem("CoC-Winner", 19469, 1, winner, true); // Purple Soul Crystal Fragment (R99-Grade)
							break;
						}
						case 3:
						{
							winner.addItem("CoC-Winner", 19511, 1, winner, true); // Yellow Soul Crystal Fragment (R95-Grade)
							break;
						}
						case 4:
						{
							winner.addItem("CoC-Winner", 19512, 1, winner, true); // Teal Soul Crystal Fragment (R95-Grade)
							break;
						}
						case 5:
						{
							winner.addItem("CoC-Winner", 19513, 1, winner, true); // Purple Soul Crystal Fragment (R95-Grade)
							break;
						}
					}
				}
				// Mysterious Belt
				else if (Rnd.get(10) < 1) // Chance to get reward (10%)
				{
					winner.addItem("CoC-Winner", 35565, 1, winner, true); // Mysterious Belt
				}
				
				// Save monthly progress.
				final int totalMarks = winner.getVariables().getInt(PlayerVariables.CEREMONY_OF_CHAOS_MARKS, 0) + marksRewarded;
				winner.getVariables().set(PlayerVariables.CEREMONY_OF_CHAOS_MARKS, totalMarks);
				if (totalMarks > GlobalVariablesManager.getInstance().getInt(GlobalVariablesManager.COC_TOP_MARKS, 0))
				{
					GlobalVariablesManager.getInstance().set(GlobalVariablesManager.COC_TOP_MARKS, totalMarks);
					GlobalVariablesManager.getInstance().set(GlobalVariablesManager.COC_TOP_MEMBER, winner.getObjectId());
				}
			}
		}
		
		for (CeremonyOfChaosMember member : members.values())
		{
			final PlayerInstance player = member.getPlayer();
			if (player != null)
			{
				// Send winner message
				if (msg != null)
				{
					player.sendPacket(msg);
				}
				
				// Send result
				player.sendPacket(new ExCuriousHouseResult(member.getResultType(), this));
				memberList.add(member);
			}
		}
		getTimers().cancelTimer("update", null, null);
		final StatSet params = new StatSet();
		params.set("time", 30);
		getTimers().addTimer("match_end_countdown", params, 30 * 1000, null, null);
		EventDispatcher.getInstance().notifyEvent(new OnCeremonyOfChaosMatchResult(winners, memberList));
	}
	
	private void teleportPlayersOut()
	{
		for (CeremonyOfChaosMember member : getMembers().values())
		{
			final PlayerInstance player = member.getPlayer();
			if (player != null)
			{
				// Leaves observer mode
				if (player.inObserverMode())
				{
					player.setObserving(false);
				}
				
				// Revive the player
				player.doRevive();
				
				// Remove Energy of Chaos
				for (SkillHolder holder : CeremonyOfChaosManager.getInstance().getVariables().getList(CeremonyOfChaosManager.INITIAL_BUFF_KEY, SkillHolder.class))
				{
					player.stopSkillEffects(holder.getSkill());
				}
				
				// Apply buffs on players
				for (SkillHolder holder : CeremonyOfChaosManager.getInstance().getVariables().getList(CeremonyOfChaosManager.END_BUFFS_KEYH, SkillHolder.class))
				{
					holder.getSkill().activateSkill(player, player);
				}
				
				// Remove quit button
				player.sendPacket(ExCuriousHouseLeave.STATIC_PACKET);
				
				// Remove spectator mode
				player.setObserving(false);
				player.sendPacket(ExCuriousHouseObserveMode.STATIC_DISABLED);
				
				// Teleport player back
				final Location lastLocation = player.getLastLocation();
				player.teleToLocation(lastLocation != null ? lastLocation : new Location(82201, 147587, -3473), null);
				
				// Restore player information
				final PlayerAppearance app = player.getAppearance();
				app.setVisibleName(null);
				app.setVisibleTitle(null);
				app.setVisibleClanData(-1, -1, -1, -1, -1);
				
				// Remove player from event
				player.removeFromEvent(this);
			}
		}
		
		clearMembers();
		_instance.destroy();
	}
	
	private void updateLifeTime(CeremonyOfChaosMember member)
	{
		member.setLifeTime(((int) (Chronos.currentTimeMillis() - _battleStartTime) / 1000));
	}
	
	public List<CeremonyOfChaosMember> getWinners()
	{
		final List<CeremonyOfChaosMember> winners = new ArrayList<>();
		final Map<Integer, CeremonyOfChaosMember> members = getMembers();
		
		//@formatter:off
		final OptionalInt winnerLifeTime = members.values().stream()
			.mapToInt(CeremonyOfChaosMember::getLifeTime)
			.max();
		
		if(winnerLifeTime.isPresent())
		{
			members.values().stream()
				.sorted(Comparator.comparingLong(CeremonyOfChaosMember::getLifeTime)
					.reversed()
					.thenComparingInt(CeremonyOfChaosMember::getScore)
					.reversed())
				.filter(member -> member.getLifeTime() == winnerLifeTime.getAsInt())
				.collect(Collectors.toCollection(() -> winners));
		}
		
		//@formatter:on
		
		return winners;
	}
	
	private void validateWinner()
	{
		final List<CeremonyOfChaosMember> winners = getWinners();
		winners.forEach(winner -> winner.setResultType(winners.size() > 1 ? CeremonyOfChaosResult.TIE : CeremonyOfChaosResult.WIN));
	}
	
	@Override
	public void onTimerEvent(String event, StatSet params, Npc npc, PlayerInstance player)
	{
		switch (event)
		{
			case "update":
			{
				final Map<Integer, CeremonyOfChaosMember> members = getMembers();
				
				final int time = (int) CeremonyOfChaosManager.getInstance().getScheduler("stopFight").getRemainingTime(TimeUnit.SECONDS);
				broadcastPacket(new ExCuriousHouseRemainTime(time));
				members.values().forEach(p -> broadcastPacket(new ExCuriousHouseMemberUpdate(p)));
				
				// Validate winner
				int count = 0;
				for (CeremonyOfChaosMember member : members.values())
				{
					if (!member.isDefeated())
					{
						count++;
					}
				}
				if (count <= 1)
				{
					stopFight();
				}
				break;
			}
			case "teleport_message1":
			{
				broadcastPacket(new SystemMessage(SystemMessageId.PROVE_YOUR_ABILITIES));
				break;
			}
			case "teleport_message2":
			{
				broadcastPacket(new SystemMessage(SystemMessageId.THERE_ARE_NO_ALLIES_HERE_EVERYONE_IS_AN_ENEMY));
				break;
			}
			case "teleport_message3":
			{
				broadcastPacket(new SystemMessage(SystemMessageId.IT_WILL_BE_A_LONELY_BATTLE_BUT_I_WISH_YOU_VICTORY));
				break;
			}
			case "match_start_countdown":
			{
				final int time = params.getInt("time", 0);
				final SystemMessage countdown = new SystemMessage(SystemMessageId.THE_MATCH_WILL_START_IN_S1_SECOND_S);
				countdown.addByte(time);
				broadcastPacket(countdown);
				
				// Reschedule
				if (time == 60)
				{
					params.set("time", 30);
					getTimers().addTimer(event, params, 30 * 1000, null, null);
				}
				else if ((time == 30) || (time == 20))
				{
					params.set("time", time - 10);
					getTimers().addTimer(event, params, 10 * 1000, null, null);
				}
				else if (time == 10)
				{
					params.set("time", 5);
					getTimers().addTimer(event, params, 5 * 1000, null, null);
				}
				else if ((time > 1) && (time <= 5))
				{
					params.set("time", time - 1);
					getTimers().addTimer(event, params, 1000, null, null);
				}
				break;
			}
			case "match_end_countdown":
			{
				final int time = params.getInt("time", 0);
				final SystemMessage countdown = new SystemMessage(SystemMessageId.IN_S1_SECOND_S_YOU_WILL_BE_MOVED_TO_WHERE_YOU_WERE_BEFORE_PARTICIPATING_IN_THE_CEREMONY_OF_CHAOS);
				countdown.addByte(time);
				broadcastPacket(countdown);
				
				// Reschedule
				if ((time == 30) || (time == 20))
				{
					params.set("time", time - 10);
					getTimers().addTimer(event, params, 10 * 1000, null, null);
				}
				else if ((time > 0) && (time <= 10))
				{
					params.set("time", time - 1);
					getTimers().addTimer(event, params, 1000, null, null);
				}
				else if (time == 0)
				{
					teleportPlayersOut();
				}
				break;
			}
		}
	}
	
	@RegisterEvent(EventType.ON_PLAYER_LOGOUT)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	private void OnPlayerLogout(OnPlayerLogout event)
	{
		final PlayerInstance player = event.getPlayer();
		if (player != null)
		{
			final Map<Integer, CeremonyOfChaosMember> members = getMembers();
			final int playerObjectId = player.getObjectId();
			if (members.containsKey(playerObjectId))
			{
				removeMember(playerObjectId);
				if (members.size() <= 1)
				{
					stopFight();
				}
			}
		}
	}
	
	@RegisterEvent(EventType.ON_CREATURE_DEATH)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onPlayerDeath(OnCreatureDeath event)
	{
		if (event.getAttacker().isPlayer() && event.getTarget().isPlayer())
		{
			final PlayerInstance attackerPlayer = event.getAttacker().getActingPlayer();
			final PlayerInstance targetPlayer = event.getTarget().getActingPlayer();
			final Map<Integer, CeremonyOfChaosMember> members = getMembers();
			final CeremonyOfChaosMember attackerMember = members.get(attackerPlayer.getObjectId());
			final CeremonyOfChaosMember targetMember = members.get(targetPlayer.getObjectId());
			final DeleteObject deleteObject = new DeleteObject(targetPlayer);
			if ((attackerMember != null) && (targetMember != null))
			{
				attackerMember.incrementScore();
				updateLifeTime(targetMember);
				
				// Mark player as defeated
				targetMember.setDefeated(true);
				
				// Delete target player
				for (CeremonyOfChaosMember member : members.values())
				{
					if (member.getObjectId() != targetPlayer.getObjectId())
					{
						deleteObject.sendTo(member.getPlayer());
					}
				}
				
				// Make the target observer
				targetPlayer.setObserving(true);
				
				// Make the target spectator
				targetPlayer.sendPacket(ExCuriousHouseObserveMode.STATIC_ENABLED);
			}
		}
	}
}
