package org.l2jmobius.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import util.Rnd;
import org.l2jmobius.gameserver.model.actor.Npc;

/**
 * @author Mobius
 */
public class RandomAnimationTaskManager
{
	private static final Map<Npc, Long> PENDING_ANIMATIONS = new ConcurrentHashMap<>();
	private static boolean _working = false;
	
	public RandomAnimationTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			final long time = Chronos.currentTimeMillis();
			for (Entry<Npc, Long> entry : PENDING_ANIMATIONS.entrySet())
			{
				if (time > entry.getValue().longValue())
				{
					final Npc npc = entry.getKey();
					if (npc.isInActiveRegion() && !npc.isDead() && !npc.isInCombat() && !npc.isMoving() && !npc.hasBlockActions())
					{
						npc.onRandomAnimation(Rnd.get(2, 3));
					}
					PENDING_ANIMATIONS.put(npc, time + (Rnd.get((npc.isAttackable() ? Config.MIN_MONSTER_ANIMATION : Config.MIN_NPC_ANIMATION), (npc.isAttackable() ? Config.MAX_MONSTER_ANIMATION : Config.MAX_NPC_ANIMATION)) * 1000));
				}
			}
			
			_working = false;
		}, 0, 1000);
	}
	
	public void add(Npc npc)
	{
		if (npc.hasRandomAnimation())
		{
			PENDING_ANIMATIONS.putIfAbsent(npc, Chronos.currentTimeMillis() + (Rnd.get((npc.isAttackable() ? Config.MIN_MONSTER_ANIMATION : Config.MIN_NPC_ANIMATION), (npc.isAttackable() ? Config.MAX_MONSTER_ANIMATION : Config.MAX_NPC_ANIMATION)) * 1000));
		}
	}
	
	public void remove(Npc npc)
	{
		PENDING_ANIMATIONS.remove(npc);
	}
	
	public static RandomAnimationTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RandomAnimationTaskManager INSTANCE = new RandomAnimationTaskManager();
	}
}
