package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author UnAfraid
 */
public class ConditionTargetLevelRange extends Condition
{
	private final int[] _levels;
	
	/**
	 * Instantiates a new condition target levels range.
	 * @param levels the {@code levels} range.
	 */
	public ConditionTargetLevelRange(int[] levels)
	{
		_levels = levels;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effected == null)
		{
			return false;
		}
		final int level = effected.getLevel();
		return (level >= _levels[0]) && (level <= _levels[1]);
	}
}
