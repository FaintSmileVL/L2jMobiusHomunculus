package org.l2jmobius.gameserver.model.events.impl.creature;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * An instantly executed event when Creature is killed by Creature.
 * @author UnAfraid
 */
public class OnCreatureDeath implements IBaseEvent
{
	private final Creature _attacker;
	private final Creature _target;
	
	public OnCreatureDeath(Creature attacker, Creature target)
	{
		_attacker = attacker;
		_target = target;
	}
	
	public Creature getAttacker()
	{
		return _attacker;
	}
	
	public Creature getTarget()
	{
		return _target;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CREATURE_DEATH;
	}
}