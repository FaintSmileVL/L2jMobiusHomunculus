package org.l2jmobius.gameserver.instancemanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import util.Rnd;
import org.l2jmobius.gameserver.model.Duel;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

public class DuelManager
{
	private static final int[] ARENAS =
	{
		147, // OlympiadGrassyArena.xml
		148, // OlympiadThreeBridgesArena.xml
		149, // OlympiadHerossVestigesArena.xml
		150, // OlympiadOrbisArena.xml
	};
	private final Map<Integer, Duel> _duels = new ConcurrentHashMap<>();
	private final AtomicInteger _currentDuelId = new AtomicInteger();
	
	protected DuelManager()
	{
	}
	
	public Duel getDuel(int duelId)
	{
		return _duels.get(duelId);
	}
	
	public void addDuel(PlayerInstance playerA, PlayerInstance playerB, int partyDuel)
	{
		if ((playerA == null) || (playerB == null))
		{
			return;
		}
		
		// return if a player has PvPFlag
		final String engagedInPvP = "The duel was canceled because a duelist engaged in PvP combat.";
		if (partyDuel == 1)
		{
			boolean playerInPvP = false;
			for (PlayerInstance temp : playerA.getParty().getMembers())
			{
				if (temp.getPvpFlag() != 0)
				{
					playerInPvP = true;
					break;
				}
			}
			if (!playerInPvP)
			{
				for (PlayerInstance temp : playerB.getParty().getMembers())
				{
					if (temp.getPvpFlag() != 0)
					{
						playerInPvP = true;
						break;
					}
				}
			}
			// A player has PvP flag
			if (playerInPvP)
			{
				for (PlayerInstance temp : playerA.getParty().getMembers())
				{
					temp.sendMessage(engagedInPvP);
				}
				for (PlayerInstance temp : playerB.getParty().getMembers())
				{
					temp.sendMessage(engagedInPvP);
				}
				return;
			}
		}
		else if ((playerA.getPvpFlag() != 0) || (playerB.getPvpFlag() != 0))
		{
			playerA.sendMessage(engagedInPvP);
			playerB.sendMessage(engagedInPvP);
			return;
		}
		final int duelId = _currentDuelId.incrementAndGet();
		_duels.put(duelId, new Duel(playerA, playerB, partyDuel, duelId));
	}
	
	public void removeDuel(Duel duel)
	{
		_duels.remove(duel.getId());
	}
	
	public void doSurrender(PlayerInstance player)
	{
		if ((player == null) || !player.isInDuel())
		{
			return;
		}
		final Duel duel = getDuel(player.getDuelId());
		duel.doSurrender(player);
	}
	
	/**
	 * Updates player states.
	 * @param player - the dying player
	 */
	public void onPlayerDefeat(PlayerInstance player)
	{
		if ((player == null) || !player.isInDuel())
		{
			return;
		}
		final Duel duel = getDuel(player.getDuelId());
		if (duel != null)
		{
			duel.onPlayerDefeat(player);
		}
	}
	
	/**
	 * Registers a buff which will be removed if the duel ends
	 * @param player
	 * @param buff
	 */
	public void onBuff(PlayerInstance player, Skill buff)
	{
		if ((player == null) || !player.isInDuel() || (buff == null))
		{
			return;
		}
		final Duel duel = getDuel(player.getDuelId());
		if (duel != null)
		{
			duel.onBuff(player, buff);
		}
	}
	
	/**
	 * Removes player from duel.
	 * @param player - the removed player
	 */
	public void onRemoveFromParty(PlayerInstance player)
	{
		if ((player == null) || !player.isInDuel())
		{
			return;
		}
		final Duel duel = getDuel(player.getDuelId());
		if (duel != null)
		{
			duel.onRemoveFromParty(player);
		}
	}
	
	/**
	 * Broadcasts a packet to the team opposing the given player.
	 * @param player
	 * @param packet
	 */
	public void broadcastToOppositTeam(PlayerInstance player, IClientOutgoingPacket packet)
	{
		if ((player == null) || !player.isInDuel())
		{
			return;
		}
		final Duel duel = getDuel(player.getDuelId());
		if (duel == null)
		{
			return;
		}
		if ((duel.getPlayerA() == null) || (duel.getPlayerB() == null))
		{
			return;
		}
		
		if (duel.getPlayerA() == player)
		{
			duel.broadcastToTeam2(packet);
		}
		else if (duel.getPlayerB() == player)
		{
			duel.broadcastToTeam1(packet);
		}
		else if (duel.isPartyDuel())
		{
			if ((duel.getPlayerA().getParty() != null) && duel.getPlayerA().getParty().getMembers().contains(player))
			{
				duel.broadcastToTeam2(packet);
			}
			else if ((duel.getPlayerB().getParty() != null) && duel.getPlayerB().getParty().getMembers().contains(player))
			{
				duel.broadcastToTeam1(packet);
			}
		}
	}
	
	/**
	 * Gets new a random Olympiad Stadium instance name.
	 * @return an instance name
	 */
	public int getDuelArena()
	{
		return ARENAS[Rnd.get(ARENAS.length)];
	}
	
	public static DuelManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final DuelManager INSTANCE = new DuelManager();
	}
}