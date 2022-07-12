package org.l2jmobius.gameserver.model.cubic.conditions;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;
import org.l2jmobius.gameserver.model.cubic.CubicInstance;

/**
 * @author UnAfraid
 */
public class HpCondition implements ICubicCondition
{
	private final HpConditionType _type;
	private final int _hpPer;
	
	public HpCondition(HpConditionType type, int hpPer)
	{
		_type = type;
		_hpPer = hpPer;
	}
	
	@Override
	public boolean test(CubicInstance cubic, Creature owner, WorldObject target)
	{
		if (target.isCreature() || target.isDoor())
		{
			final double hpPer = (target.isDoor() ? (DoorInstance) target : (Creature) target).getCurrentHpPercent();
			switch (_type)
			{
				case GREATER:
				{
					return hpPer > _hpPer;
				}
				case LESSER:
				{
					return hpPer < _hpPer;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " chance: " + _hpPer;
	}
	
	public enum HpConditionType
	{
		GREATER,
		LESSER;
	}
}
