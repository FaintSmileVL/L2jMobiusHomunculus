package org.l2jmobius.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author Mobius
 */
public class DecayTaskManager
{
	private static final Map<Creature, Long> DECAY_SCHEDULES = new ConcurrentHashMap<>();
	private static boolean _working = false;
	
	public DecayTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			final long time = Chronos.currentTimeMillis();
			for (Entry<Creature, Long> entry : DECAY_SCHEDULES.entrySet())
			{
				if (time > entry.getValue().longValue())
				{
					final Creature creature = entry.getKey();
					DECAY_SCHEDULES.remove(creature);
					creature.onDecay();
				}
			}
			
			_working = false;
		}, 0, 1000);
	}
	
	/**
	 * Adds a decay task for the specified character.<br>
	 * If the decay task already exists it cancels it and re-adds it.
	 * @param creature the creature
	 */
	public void add(Creature creature)
	{
		if (creature == null)
		{
			return;
		}
		
		long delay;
		if (creature.getTemplate() instanceof NpcTemplate)
		{
			delay = ((NpcTemplate) creature.getTemplate()).getCorpseTime();
		}
		else
		{
			delay = Config.DEFAULT_CORPSE_TIME;
		}
		
		if (creature.isAttackable() && (((Attackable) creature).isSpoiled() || ((Attackable) creature).isSeeded()))
		{
			delay += Config.SPOILED_CORPSE_EXTEND_TIME;
		}
		
		// Add to decay schedules.
		DECAY_SCHEDULES.put(creature, Chronos.currentTimeMillis() + (delay * 1000));
	}
	
	/**
	 * Cancels the decay task of the specified character.
	 * @param creature the creature
	 */
	public void cancel(Creature creature)
	{
		DECAY_SCHEDULES.remove(creature);
	}
	
	/**
	 * Gets the remaining time of the specified character's decay task.
	 * @param creature the creature
	 * @return if a decay task exists the remaining time, {@code Long.MAX_VALUE} otherwise
	 */
	public long getRemainingTime(Creature creature)
	{
		final Long time = DECAY_SCHEDULES.get(creature);
		return time != null ? time.longValue() - Chronos.currentTimeMillis() : Long.MAX_VALUE;
	}
	
	@Override
	public String toString()
	{
		final StringBuilder ret = new StringBuilder();
		ret.append("============= DecayTask Manager Report ============");
		ret.append(Config.EOL);
		ret.append("Tasks count: ");
		ret.append(DECAY_SCHEDULES.size());
		ret.append(Config.EOL);
		ret.append("Tasks dump:");
		ret.append(Config.EOL);
		
		final long time = Chronos.currentTimeMillis();
		for (Entry<Creature, Long> entry : DECAY_SCHEDULES.entrySet())
		{
			ret.append("Class/Name: ");
			ret.append(entry.getKey().getClass().getSimpleName());
			ret.append('/');
			ret.append(entry.getKey().getName());
			ret.append(" decay timer: ");
			ret.append(entry.getValue().longValue() - time);
			ret.append(Config.EOL);
		}
		
		return ret.toString();
	}
	
	public static DecayTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final DecayTaskManager INSTANCE = new DecayTaskManager();
	}
}
