package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerActiveSkillId.
 * @author DrHouse
 */
public class ConditionPlayerActiveSkillId extends Condition
{
	private final int _skillId;
	private final int _skillLevel;
	
	/**
	 * Instantiates a new condition player active skill id.
	 * @param skillId the skill id
	 */
	public ConditionPlayerActiveSkillId(int skillId)
	{
		_skillId = skillId;
		_skillLevel = -1;
	}
	
	/**
	 * Instantiates a new condition player active skill id.
	 * @param skillId the skill id
	 * @param skillLevel the skill level
	 */
	public ConditionPlayerActiveSkillId(int skillId, int skillLevel)
	{
		_skillId = skillId;
		_skillLevel = skillLevel;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final Skill knownSkill = effector.getKnownSkill(_skillId);
		if (knownSkill != null)
		{
			return (_skillLevel == -1) || (_skillLevel <= knownSkill.getLevel());
		}
		return false;
	}
}
