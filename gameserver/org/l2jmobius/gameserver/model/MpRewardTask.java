package org.l2jmobius.gameserver.model;

import java.util.concurrent.ScheduledFuture;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author UnAfraid
 */
public class MpRewardTask
{
	private int _count;
	private final double _value;
	private final ScheduledFuture<?> _task;
	private final Creature _creature;
	
	public MpRewardTask(Creature creature, Npc npc)
	{
		final NpcTemplate template = npc.getTemplate();
		_creature = creature;
		_count = template.getMpRewardTicks();
		_value = calculateBaseValue(npc, creature);
		_task = ThreadPool.scheduleAtFixedRate(this::run, Config.EFFECT_TICK_RATIO, Config.EFFECT_TICK_RATIO);
	}
	
	/**
	 * @param npc
	 * @param creature
	 * @return
	 */
	private double calculateBaseValue(Npc npc, Creature creature)
	{
		final NpcTemplate template = npc.getTemplate();
		switch (template.getMpRewardType())
		{
			case PER:
			{
				return (creature.getMaxMp() * (template.getMpRewardValue() / 100)) / template.getMpRewardTicks();
			}
		}
		return template.getMpRewardValue() / template.getMpRewardTicks();
	}
	
	private void run()
	{
		if ((--_count <= 0) || (_creature.isPlayer() && !_creature.getActingPlayer().isOnline()))
		{
			_task.cancel(false);
			return;
		}
		
		_creature.setCurrentMp(_creature.getCurrentMp() + _value);
	}
}
