package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * This condition becomes true whether the player is transformed and the transformation Id match the parameter or<br>
 * the parameter is -1 which returns true if player is transformed regardless the transformation Id.
 * @author Zoey76
 */
public class ConditionPlayerTransformationId extends Condition
{
	private final int _id;
	
	/**
	 * Instantiates a new condition player is transformed.
	 * @param id the transformation Id.
	 */
	public ConditionPlayerTransformationId(int id)
	{
		_id = id;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return _id == -1 ? effector.isTransformed() : effector.getTransformationId() == _id;
	}
}
