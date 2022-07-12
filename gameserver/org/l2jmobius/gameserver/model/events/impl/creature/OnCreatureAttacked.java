package org.l2jmobius.gameserver.model.events.impl.creature;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * An instantly executed event when Creature is attacked by Creature.
 * @author UnAfraid
 */
public class OnCreatureAttacked implements IBaseEvent
{
	private final Creature _attacker;
	private final Creature _target;
	private final Skill _skill;
	
	public OnCreatureAttacked(Creature attacker, Creature target, Skill skill)
	{
		_attacker = attacker;
		_target = target;
		_skill = skill;
	}
	
	public Creature getAttacker()
	{
		return _attacker;
	}
	
	public Creature getTarget()
	{
		return _target;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CREATURE_ATTACKED;
	}
}