package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetLevel.
 * @author mkizub
 */
public class ConditionTargetLevel extends Condition
{
	private final int _level;
	
	/**
	 * Instantiates a new condition target level.
	 * @param level the level
	 */
	public ConditionTargetLevel(int level)
	{
		_level = level;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effected == null)
		{
			return false;
		}
		return effected.getLevel() >= _level;
	}
}
