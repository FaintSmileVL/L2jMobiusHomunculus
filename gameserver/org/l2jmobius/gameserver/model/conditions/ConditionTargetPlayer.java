package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Sdw
 */
public class ConditionTargetPlayer extends Condition
{
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return effected.isPlayer();
	}
}
