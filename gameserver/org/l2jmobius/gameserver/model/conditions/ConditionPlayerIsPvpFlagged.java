package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerIsPvpFlagged.
 * @author Mobius
 */
public class ConditionPlayerIsPvpFlagged extends Condition
{
	private final boolean _value;
	
	/**
	 * Instantiates a new condition player is PvP flagged.
	 * @param value the value
	 */
	public ConditionPlayerIsPvpFlagged(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (effector.getActingPlayer() != null) && ((effector.getActingPlayer().getPvpFlag() > 0) == _value);
	}
}
