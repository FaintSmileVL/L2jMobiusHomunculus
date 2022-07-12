package org.l2jmobius.gameserver.taskmanager;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.ai.CtrlIntention;
import org.l2jmobius.gameserver.geoengine.GeoEngine;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.instance.MonsterInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.serverpackets.autoplay.ExAutoPlayDoMacro;

/**
 * @author Mobius
 */
public class AutoPlayTaskManager
{
	private static final Set<PlayerInstance> PLAYERS = ConcurrentHashMap.newKeySet();
	private static boolean _working = false;
	
	public AutoPlayTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			PLAY: for (PlayerInstance player : PLAYERS)
			{
				if (!player.isOnline() || player.isInOfflineMode() || !Config.ENABLE_AUTO_PLAY)
				{
					stopAutoPlay(player);
					continue PLAY;
				}
				
				if (player.isCastingNow() || (player.getQueuedSkill() != null))
				{
					continue PLAY;
				}
				
				// Skip thinking.
				final WorldObject target = player.getTarget();
				if ((target != null) && target.isMonster())
				{
					final MonsterInstance monster = (MonsterInstance) target;
					if (monster.isAlikeDead())
					{
						player.setTarget(null);
					}
					else if (monster.getTarget() == player)
					{
						// We take granted that mage classes do not auto hit.
						if (player.isMageClass())
						{
							continue PLAY;
						}
						
						// Check if actually attacking.
						if (player.hasAI() && player.getAI().isAutoAttacking() && !player.isAttackingNow() && !player.isCastingNow() && !player.isMoving())
						{
							player.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, monster);
						}
						continue PLAY;
					}
				}
				
				// Pickup.
				if (player.getAutoPlaySettings().doPickup())
				{
					PICKUP: for (ItemInstance droppedItem : World.getInstance().getVisibleObjectsInRange(player, ItemInstance.class, 200))
					{
						// Check if item is reachable.
						if ((droppedItem == null) //
							|| (!droppedItem.isSpawned()) //
							|| !GeoEngine.getInstance().canMoveToTarget(player.getX(), player.getY(), player.getZ(), droppedItem.getX(), droppedItem.getY(), droppedItem.getZ(), player.getInstanceWorld()))
						{
							continue PICKUP;
						}
						
						// Move to item.
						if (player.calculateDistance2D(droppedItem) > 70)
						{
							if (!player.isMoving())
							{
								player.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, droppedItem);
							}
							continue PLAY;
						}
						
						// Try to pick it up.
						if (!droppedItem.isProtected() || (droppedItem.getOwnerId() == player.getObjectId()))
						{
							player.doPickupItem(droppedItem);
							continue PLAY; // Avoid pickup being skipped.
						}
					}
				}
				
				// Find target.
				MonsterInstance monster = null;
				double closestDistance = Double.MAX_VALUE;
				TARGET: for (MonsterInstance nearby : World.getInstance().getVisibleObjectsInRange(player, MonsterInstance.class, player.getAutoPlaySettings().isLongRange() ? 1400 : 600))
				{
					// Skip unavailable monsters.
					if ((nearby == null) || nearby.isAlikeDead())
					{
						continue TARGET;
					}
					// Check monster target.
					if (player.getAutoPlaySettings().isRespectfulHunting() && (nearby.getTarget() != null) && (nearby.getTarget() != player) && !player.getServitors().containsKey(nearby.getTarget().getObjectId()))
					{
						continue TARGET;
					}
					// Check if monster is reachable.
					if (nearby.isAutoAttackable(player) //
						&& GeoEngine.getInstance().canSeeTarget(player, nearby)//
						&& GeoEngine.getInstance().canMoveToTarget(player.getX(), player.getY(), player.getZ(), nearby.getX(), nearby.getY(), nearby.getZ(), player.getInstanceWorld()))
					{
						final double monsterDistance = player.calculateDistance2D(nearby);
						if (monsterDistance < closestDistance)
						{
							monster = nearby;
							closestDistance = monsterDistance;
						}
					}
				}
				
				// New target was assigned.
				if (monster != null)
				{
					player.setTarget(monster);
					
					// We take granted that mage classes do not auto hit.
					if (player.isMageClass())
					{
						continue PLAY;
					}
					
					player.sendPacket(ExAutoPlayDoMacro.STATIC_PACKET);
				}
			}
			
			_working = false;
		}, 1000, 1000);
	}
	
	public void doAutoPlay(PlayerInstance player)
	{
		if (!PLAYERS.contains(player))
		{
			player.onActionRequest();
			PLAYERS.add(player);
		}
	}
	
	public void stopAutoPlay(PlayerInstance player)
	{
		PLAYERS.remove(player);
	}
	
	public static AutoPlayTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AutoPlayTaskManager INSTANCE = new AutoPlayTaskManager();
	}
}
