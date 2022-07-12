package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * Effect duration holder.
 * @author Zoey76
 */
public class EffectDurationHolder
{
	private final int _skillId;
	private final int _skillLevel;
	private final int _duration;
	
	/**
	 * Effect duration holder constructor.
	 * @param skill the skill to get the data
	 * @param duration the effect duration
	 */
	public EffectDurationHolder(Skill skill, int duration)
	{
		_skillId = skill.getDisplayId();
		_skillLevel = skill.getDisplayLevel();
		_duration = duration;
	}
	
	/**
	 * Get the effect's skill Id.
	 * @return the skill Id
	 */
	public int getSkillId()
	{
		return _skillId;
	}
	
	/**
	 * Get the effect's skill level.
	 * @return the skill level
	 */
	public int getSkillLevel()
	{
		return _skillLevel;
	}
	
	/**
	 * Get the effect's duration.
	 * @return the duration in <b>seconds</b>
	 */
	public int getDuration()
	{
		return _duration;
	}
}