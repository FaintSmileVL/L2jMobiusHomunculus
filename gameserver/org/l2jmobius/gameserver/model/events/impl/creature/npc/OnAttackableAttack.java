package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * An instantly executed event when Attackable is attacked by PlayerInstance.
 * @author UnAfraid
 */
public class OnAttackableAttack implements IBaseEvent
{
	private final PlayerInstance _attacker;
	private final Attackable _target;
	private final int _damage;
	private final Skill _skill;
	private final boolean _isSummon;
	
	public OnAttackableAttack(PlayerInstance attacker, Attackable target, int damage, Skill skill, boolean isSummon)
	{
		_attacker = attacker;
		_target = target;
		_damage = damage;
		_skill = skill;
		_isSummon = isSummon;
	}
	
	public PlayerInstance getAttacker()
	{
		return _attacker;
	}
	
	public Attackable getTarget()
	{
		return _target;
	}
	
	public int getDamage()
	{
		return _damage;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
	
	public boolean isSummon()
	{
		return _isSummon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ATTACKABLE_ATTACK;
	}
}