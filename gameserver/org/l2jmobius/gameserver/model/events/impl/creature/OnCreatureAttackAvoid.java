package org.l2jmobius.gameserver.model.events.impl.creature;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * An instantly executed event when Creature attack miss Creature.
 * @author Zealar
 */
public class OnCreatureAttackAvoid implements IBaseEvent
{
	private final Creature _attacker;
	private final Creature _target;
	private final boolean _damageOverTime;
	
	/**
	 * @param attacker who attack
	 * @param target who avoid
	 * @param isDot is dot damage
	 */
	public OnCreatureAttackAvoid(Creature attacker, Creature target, boolean isDot)
	{
		_attacker = attacker;
		_target = target;
		_damageOverTime = isDot;
	}
	
	public Creature getAttacker()
	{
		return _attacker;
	}
	
	public Creature getTarget()
	{
		return _target;
	}
	
	/**
	 * @return
	 */
	public boolean isDamageOverTime()
	{
		return _damageOverTime;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CREATURE_ATTACK_AVOID;
	}
}