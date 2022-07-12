package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionUsingSkill.
 * @author mkizub
 */
public class ConditionUsingSkill extends Condition
{
	private final int _skillId;
	
	/**
	 * Instantiates a new condition using skill.
	 * @param skillId the skill id
	 */
	public ConditionUsingSkill(int skillId)
	{
		_skillId = skillId;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (skill == null)
		{
			return false;
		}
		return skill.getId() == _skillId;
	}
}
