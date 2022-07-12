package org.l2jmobius.gameserver.model.cubic.conditions;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;
import org.l2jmobius.gameserver.model.cubic.CubicInstance;

/**
 * @author UnAfraid
 */
public class HealthCondition implements ICubicCondition
{
	private final int _min;
	private final int _max;
	
	public HealthCondition(int min, int max)
	{
		_min = min;
		_max = max;
	}
	
	@Override
	public boolean test(CubicInstance cubic, Creature owner, WorldObject target)
	{
		if (target.isCreature() || target.isDoor())
		{
			final double hpPer = (target.isDoor() ? (DoorInstance) target : (Creature) target).getCurrentHpPercent();
			return (hpPer > _min) && (hpPer < _max);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " min: " + _min + " max: " + _max;
	}
}
