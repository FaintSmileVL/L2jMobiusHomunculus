package org.l2jmobius.gameserver.taskmanager;

import static org.l2jmobius.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.ThreadPool;
import util.Rnd;
import org.l2jmobius.gameserver.ai.CreatureAI;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Summon;

/**
 * @author Mobius
 */
public class CreatureFollowTaskManager
{
	private static final Map<Creature, Integer> NORMAL_FOLLOW_CREATURES = new ConcurrentHashMap<>();
	private static final Map<Creature, Integer> ATTACK_FOLLOW_CREATURES = new ConcurrentHashMap<>();
	private static boolean _workingNormal = false;
	private static boolean _workingAttack = false;
	
	public CreatureFollowTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_workingNormal)
			{
				return;
			}
			_workingNormal = true;
			
			for (Entry<Creature, Integer> entry : NORMAL_FOLLOW_CREATURES.entrySet())
			{
				follow(entry.getKey(), entry.getValue().intValue());
			}
			
			_workingNormal = false;
		}, 1000, 1000);
		
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_workingAttack)
			{
				return;
			}
			_workingAttack = true;
			
			for (Entry<Creature, Integer> entry : ATTACK_FOLLOW_CREATURES.entrySet())
			{
				follow(entry.getKey(), entry.getValue().intValue());
			}
			
			_workingAttack = false;
		}, 500, 500);
	}
	
	private void follow(Creature creature, int range)
	{
		try
		{
			if (creature.hasAI())
			{
				final CreatureAI ai = creature.getAI();
				if (ai != null)
				{
					final WorldObject followTarget = ai.getTarget();
					if (followTarget == null)
					{
						if (creature.isSummon())
						{
							((Summon) creature).setFollowStatus(false);
						}
						ai.setIntention(AI_INTENTION_IDLE);
						return;
					}
					
					final int followRange = range == -1 ? Rnd.get(50, 100) : range;
					if (!creature.isInsideRadius3D(followTarget, followRange))
					{
						if (!creature.isInsideRadius3D(followTarget, 3000))
						{
							// If the target is too far (maybe also teleported).
							if (creature.isSummon())
							{
								((Summon) creature).setFollowStatus(false);
							}
							ai.setIntention(AI_INTENTION_IDLE);
							return;
						}
						ai.moveToPawn(followTarget, followRange);
					}
				}
				else
				{
					remove(creature);
				}
			}
			else
			{
				remove(creature);
			}
		}
		catch (Exception e)
		{
			// Ignore.
		}
	}
	
	public boolean isFollowing(Creature creature)
	{
		return NORMAL_FOLLOW_CREATURES.containsKey(creature) || ATTACK_FOLLOW_CREATURES.containsKey(creature);
	}
	
	public void addNormalFollow(Creature creature, int range)
	{
		NORMAL_FOLLOW_CREATURES.putIfAbsent(creature, range);
		follow(creature, range);
	}
	
	public void addAttackFollow(Creature creature, int range)
	{
		ATTACK_FOLLOW_CREATURES.putIfAbsent(creature, range);
		follow(creature, range);
	}
	
	public void remove(Creature creature)
	{
		NORMAL_FOLLOW_CREATURES.remove(creature);
		ATTACK_FOLLOW_CREATURES.remove(creature);
	}
	
	public static CreatureFollowTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final CreatureFollowTaskManager INSTANCE = new CreatureFollowTaskManager();
	}
}
