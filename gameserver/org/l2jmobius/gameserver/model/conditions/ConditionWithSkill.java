package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionWithSkill.
 * @author Steuf
 */
public class ConditionWithSkill extends Condition
{
	private final boolean _skill;
	
	/**
	 * Instantiates a new condition with skill.
	 * @param skill the skill
	 */
	public ConditionWithSkill(boolean skill)
	{
		_skill = skill;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (skill != null) == _skill;
	}
}
