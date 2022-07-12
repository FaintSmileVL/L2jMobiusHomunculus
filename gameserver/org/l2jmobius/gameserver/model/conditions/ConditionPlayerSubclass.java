package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerSubclass.
 */
public class ConditionPlayerSubclass extends Condition
{
	private final boolean _value;
	
	/**
	 * Instantiates a new condition player subclass.
	 * @param value the value
	 */
	public ConditionPlayerSubclass(boolean value)
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
		return effector.getActingPlayer().isSubClassActive() == _value;
	}
}
