package org.l2jmobius.gameserver.model.holders;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.data.xml.SkillTreeData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.interfaces.ISkillsHolder;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author UnAfraid
 */
public class PlayerSkillHolder implements ISkillsHolder
{
	private final Map<Integer, Skill> _skills = new HashMap<>();
	
	public PlayerSkillHolder(PlayerInstance player)
	{
		for (Skill skill : player.getSkills().values())
		{
			// Adding only skills that can be learned by the player.
			if (SkillTreeData.getInstance().isSkillAllowed(player, skill))
			{
				addSkill(skill);
			}
		}
	}
	
	/**
	 * @return the map containing this character skills.
	 */
	@Override
	public Map<Integer, Skill> getSkills()
	{
		return _skills;
	}
	
	/**
	 * Add a skill to the skills map.
	 * @param skill
	 */
	@Override
	public Skill addSkill(Skill skill)
	{
		return _skills.put(skill.getId(), skill);
	}
	
	/**
	 * Return the level of a skill owned by the Creature.
	 * @param skillId The identifier of the Skill whose level must be returned
	 * @return The level of the Skill identified by skillId
	 */
	@Override
	public int getSkillLevel(int skillId)
	{
		final Skill skill = getKnownSkill(skillId);
		return (skill == null) ? 0 : skill.getLevel();
	}
	
	/**
	 * @param skillId The identifier of the Skill to check the knowledge
	 * @return the skill from the known skill.
	 */
	@Override
	public Skill getKnownSkill(int skillId)
	{
		return _skills.get(skillId);
	}
	
	public Skill removeSkill(Skill skill)
	{
		return _skills.remove(skill.getId());
	}
}
