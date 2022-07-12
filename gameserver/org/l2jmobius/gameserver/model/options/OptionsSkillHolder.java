package org.l2jmobius.gameserver.model.options;

import org.l2jmobius.gameserver.model.holders.SkillHolder;

/**
 * @author UnAfraid
 */
public class OptionsSkillHolder extends SkillHolder
{
	private final OptionsSkillType _type;
	private final double _chance;
	
	/**
	 * @param skillId
	 * @param skillLevel
	 * @param type
	 * @param chance
	 */
	public OptionsSkillHolder(int skillId, int skillLevel, double chance, OptionsSkillType type)
	{
		super(skillId, skillLevel);
		_chance = chance;
		_type = type;
	}
	
	public OptionsSkillType getSkillType()
	{
		return _type;
	}
	
	public double getChance()
	{
		return _chance;
	}
}
