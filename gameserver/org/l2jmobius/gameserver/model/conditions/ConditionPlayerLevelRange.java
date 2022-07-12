package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Zoey76
 */
public class ConditionPlayerLevelRange extends Condition
{
	private final int[] _levels;
	
	/**
	 * Instantiates a new condition player levels range.
	 * @param levels the {@code levels} range.
	 */
	public ConditionPlayerLevelRange(int[] levels)
	{
		_levels = levels;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final int level = effector.getLevel();
		return ((level >= _levels[0]) && (level <= _levels[1]));
	}
}
