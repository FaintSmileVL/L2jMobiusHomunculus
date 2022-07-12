package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Nyaran
 */
public class ConditionPlayerVehicleMounted extends Condition
{
	private final boolean _value;
	
	/**
	 * @param value the value
	 */
	public ConditionPlayerVehicleMounted(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effector.getActingPlayer() == null)
		{
			return true;
		}
		return (effector.getActingPlayer().isInVehicle() == _value);
	}
}
