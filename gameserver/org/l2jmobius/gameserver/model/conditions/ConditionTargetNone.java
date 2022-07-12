package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetNone.
 * @author mkizub
 */
public class ConditionTargetNone extends Condition
{
	/**
	 * Instantiates a new condition target none.
	 */
	public ConditionTargetNone()
	{
		//
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return effected == null;
	}
}
