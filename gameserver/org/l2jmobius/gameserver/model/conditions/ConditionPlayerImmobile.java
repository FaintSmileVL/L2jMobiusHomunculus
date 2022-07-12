package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Sdw
 */
public class ConditionPlayerImmobile extends Condition
{
	private final boolean _value;
	
	public ConditionPlayerImmobile(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final boolean isImmobile = !effector.isMovementDisabled();
		return _value == isImmobile;
	}
}
