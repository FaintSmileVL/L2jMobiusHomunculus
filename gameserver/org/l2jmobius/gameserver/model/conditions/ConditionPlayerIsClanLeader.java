package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerIsClanLeader.
 */
public class ConditionPlayerIsClanLeader extends Condition
{
	private final boolean _value;
	
	/**
	 * Instantiates a new condition player is clan leader.
	 * @param value the value
	 */
	public ConditionPlayerIsClanLeader(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effector.getActingPlayer() == null)
		{
			return false;
		}
		return (effector.getActingPlayer().isClanLeader() == _value);
	}
}
