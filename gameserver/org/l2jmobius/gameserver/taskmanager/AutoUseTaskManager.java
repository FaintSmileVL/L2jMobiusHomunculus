package org.l2jmobius.gameserver.taskmanager;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.data.xml.ActionData;
import org.l2jmobius.gameserver.handler.IItemHandler;
import org.l2jmobius.gameserver.handler.IPlayerActionHandler;
import org.l2jmobius.gameserver.handler.ItemHandler;
import org.l2jmobius.gameserver.handler.PlayerActionHandler;
import org.l2jmobius.gameserver.model.ActionDataHolder;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.effects.AbstractEffect;
import org.l2jmobius.gameserver.model.holders.ItemSkillHolder;
import org.l2jmobius.gameserver.model.items.EtcItem;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.AbnormalType;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.skills.targets.AffectScope;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExBasicActionList;

/**
 * @author Mobius
 */
public class AutoUseTaskManager
{
	private static final Set<PlayerInstance> PLAYERS = ConcurrentHashMap.newKeySet();
	private static boolean _working = false;
	
	public AutoUseTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			for (PlayerInstance player : PLAYERS)
			{
				if (!player.isOnline() || player.isInOfflineMode())
				{
					stopAutoUseTask(player);
					continue;
				}
				
				if (player.hasBlockActions() || player.isControlBlocked() || player.isAlikeDead())
				{
					continue;
				}
				
				final boolean isInPeaceZone = player.isInsideZone(ZoneId.PEACE);
				
				if (Config.ENABLE_AUTO_ITEM && !isInPeaceZone)
				{
					ITEMS: for (Integer itemId : player.getAutoUseSettings().getAutoSupplyItems())
					{
						final ItemInstance item = player.getInventory().getItemByItemId(itemId.intValue());
						if (item == null)
						{
							player.getAutoUseSettings().getAutoSupplyItems().remove(itemId);
							continue ITEMS; // TODO: break?
						}
						
						final Item it = item.getItem();
						if (it != null)
						{
							final List<ItemSkillHolder> skills = it.getAllSkills();
							if (skills != null)
							{
								for (ItemSkillHolder itemSkillHolder : skills)
								{
									final Skill skill = itemSkillHolder.getSkill();
									if (player.isAffectedBySkill(skill.getId()) || player.hasSkillReuse(skill.getReuseHashCode()) || !skill.checkCondition(player, player, false))
									{
										continue ITEMS;
									}
								}
							}
						}
						
						final int reuseDelay = item.getReuseDelay();
						if ((reuseDelay <= 0) || (player.getItemRemainingReuseTime(item.getObjectId()) <= 0))
						{
							final EtcItem etcItem = item.getEtcItem();
							final IItemHandler handler = ItemHandler.getInstance().getHandler(etcItem);
							if ((handler != null) && handler.useItem(player, item, false) && (reuseDelay > 0))
							{
								player.addTimeStampItem(item, reuseDelay);
							}
						}
					}
				}
				
				if (Config.ENABLE_AUTO_POTION && !isInPeaceZone && (player.getCurrentHpPercent() <= player.getAutoPlaySettings().getAutoPotionPercent()))
				{
					POTIONS: for (Integer itemId : player.getAutoUseSettings().getAutoPotionItems())
					{
						final ItemInstance item = player.getInventory().getItemByItemId(itemId.intValue());
						if (item == null)
						{
							player.getAutoUseSettings().getAutoPotionItems().remove(itemId);
							continue POTIONS; // TODO: break?
						}
						final int reuseDelay = item.getReuseDelay();
						if ((reuseDelay <= 0) || (player.getItemRemainingReuseTime(item.getObjectId()) <= 0))
						{
							final EtcItem etcItem = item.getEtcItem();
							final IItemHandler handler = ItemHandler.getInstance().getHandler(etcItem);
							if ((handler != null) && handler.useItem(player, item, false) && (reuseDelay > 0))
							{
								player.addTimeStampItem(item, reuseDelay);
							}
						}
					}
				}
				
				if (Config.ENABLE_AUTO_BUFF && !player.isMoving())
				{
					SKILLS: for (Integer skillId : player.getAutoUseSettings().getAutoSkills())
					{
						final Skill skill = player.getKnownSkill(skillId.intValue());
						if (skill == null)
						{
							player.getAutoUseSettings().getAutoSkills().remove(skillId);
							continue SKILLS; // TODO: break?
						}
						
						final WorldObject target = player.getTarget();
						// Casting on self stops movement.
						if (target == player)
						{
							continue SKILLS;
						}
						// Check bad skill target.
						if (skill.isBad())
						{
							if (target == null)
							{
								continue SKILLS;
							}
						}
						// Fixes start area issue.
						else if (isInPeaceZone)
						{
							continue SKILLS;
						}
						
						if (!player.isAffectedBySkill(skillId.intValue()) && !player.hasSkillReuse(skill.getReuseHashCode()) && skill.checkCondition(player, player, false))
						{
							// Summon check.
							if (skill.getAffectScope() == AffectScope.SUMMON_EXCEPT_MASTER)
							{
								if (!player.hasServitors()) // Is this check truly needed?
								{
									continue SKILLS;
								}
								int occurrences = 0;
								for (Summon servitor : player.getServitors().values())
								{
									if (servitor.isAffectedBySkill(skillId.intValue()))
									{
										occurrences++;
									}
								}
								if (occurrences == player.getServitors().size())
								{
									continue SKILLS;
								}
							}
							
							// Check non bad skill target.
							if (!skill.isBad() && ((target == null) || !target.isPlayable()))
							{
								final WorldObject savedTarget = target;
								player.setTarget(player);
								player.doCast(skill);
								player.setTarget(savedTarget);
							}
							else if (player.isMageClass())
							{
								player.useMagic(skill, null, false, false);
							}
							else
							{
								player.doCast(skill);
							}
						}
					}
					
					ACTIONS: for (Integer actionId : player.getAutoUseSettings().getAutoActions())
					{
						final BuffInfo info = player.getEffectList().getFirstBuffInfoByAbnormalType(AbnormalType.BOT_PENALTY);
						if (info != null)
						{
							for (AbstractEffect effect : info.getEffects())
							{
								if (!effect.checkCondition(actionId))
								{
									player.sendPacket(SystemMessageId.YOU_HAVE_BEEN_REPORTED_AS_AN_ILLEGAL_PROGRAM_USER_SO_YOUR_ACTIONS_HAVE_BEEN_RESTRICTED);
									break ACTIONS;
								}
							}
						}
						
						// Do not allow to do some action if player is transformed.
						if (player.isTransformed())
						{
							final int[] allowedActions = player.isTransformed() ? ExBasicActionList.ACTIONS_ON_TRANSFORM : ExBasicActionList.DEFAULT_ACTION_LIST;
							if (Arrays.binarySearch(allowedActions, actionId) < 0)
							{
								continue ACTIONS;
							}
						}
						
						final ActionDataHolder actionHolder = ActionData.getInstance().getActionData(actionId);
						if (actionHolder != null)
						{
							final IPlayerActionHandler actionHandler = PlayerActionHandler.getInstance().getHandler(actionHolder.getHandler());
							if (actionHandler != null)
							{
								actionHandler.useAction(player, actionHolder, false, false);
							}
						}
					}
				}
			}
			
			_working = false;
		}, 1000, 1000);
	}
	
	public void startAutoUseTask(PlayerInstance player)
	{
		if (!PLAYERS.contains(player))
		{
			PLAYERS.add(player);
		}
	}
	
	public void stopAutoUseTask(PlayerInstance player)
	{
		if (player.getAutoUseSettings().isEmpty() || !player.isOnline() || player.isInOfflineMode())
		{
			PLAYERS.remove(player);
		}
	}
	
	public void addAutoSupplyItem(PlayerInstance player, int itemId)
	{
		player.getAutoUseSettings().getAutoSupplyItems().add(itemId);
		startAutoUseTask(player);
	}
	
	public void removeAutoSupplyItem(PlayerInstance player, int itemId)
	{
		player.getAutoUseSettings().getAutoSupplyItems().remove(itemId);
		stopAutoUseTask(player);
	}
	
	public void addAutoPotionItem(PlayerInstance player, int itemId)
	{
		player.getAutoUseSettings().getAutoPotionItems().add(itemId);
		startAutoUseTask(player);
	}
	
	public void removeAutoPotionItem(PlayerInstance player, int itemId)
	{
		player.getAutoUseSettings().getAutoPotionItems().remove(itemId);
		stopAutoUseTask(player);
	}
	
	public void addAutoSkill(PlayerInstance player, int skillId)
	{
		player.getAutoUseSettings().getAutoSkills().add(skillId);
		startAutoUseTask(player);
	}
	
	public void removeAutoSkill(PlayerInstance player, int skillId)
	{
		player.getAutoUseSettings().getAutoSkills().remove(skillId);
		stopAutoUseTask(player);
	}
	
	public void addAutoAction(PlayerInstance player, Integer actionId)
	{
		player.getAutoUseSettings().getAutoActions().add(actionId);
		startAutoUseTask(player);
	}
	
	public void removeAutoAction(PlayerInstance player, int actionId)
	{
		player.getAutoUseSettings().getAutoActions().remove(actionId);
		stopAutoUseTask(player);
	}
	
	public static AutoUseTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AutoUseTaskManager INSTANCE = new AutoUseTaskManager();
	}
}
