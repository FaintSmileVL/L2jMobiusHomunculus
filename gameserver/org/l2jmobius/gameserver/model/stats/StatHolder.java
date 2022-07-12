package org.l2jmobius.gameserver.model.stats;

import java.util.function.BiPredicate;

import org.l2jmobius.gameserver.model.actor.Creature;

/**
 * @author UnAfraid
 */
public class StatHolder
{
	private final Stat _stat;
	private final double _value;
	private final BiPredicate<Creature, StatHolder> _condition;
	
	public StatHolder(Stat stat, double value, BiPredicate<Creature, StatHolder> condition)
	{
		_stat = stat;
		_value = value;
		_condition = condition;
	}
	
	public StatHolder(Stat stat, double value)
	{
		this(stat, value, null);
	}
	
	public Stat getStat()
	{
		return _stat;
	}
	
	public double getValue()
	{
		return _value;
	}
	
	public boolean verifyCondition(Creature creature)
	{
		return (_condition == null) || _condition.test(creature, this);
	}
}
