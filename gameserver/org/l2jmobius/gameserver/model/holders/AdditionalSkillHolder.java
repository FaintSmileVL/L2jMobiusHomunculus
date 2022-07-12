package org.l2jmobius.gameserver.model.holders;

/**
 * @author UnAfraid
 */
public class AdditionalSkillHolder extends SkillHolder
{
	private final int _minLevel;
	
	public AdditionalSkillHolder(int skillId, int skillLevel, int minLevel)
	{
		super(skillId, skillLevel);
		_minLevel = minLevel;
	}
	
	public int getMinLevel()
	{
		return _minLevel;
	}
}
