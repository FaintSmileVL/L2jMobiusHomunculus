package org.l2jmobius.gameserver.model.holders;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Mobius
 */
public class AgathionSkillHolder
{
	private final Map<Integer, List<SkillHolder>> _mainSkill;
	private final Map<Integer, List<SkillHolder>> _subSkill;
	
	public AgathionSkillHolder(Map<Integer, List<SkillHolder>> mainSkill, Map<Integer, List<SkillHolder>> subSkill)
	{
		_mainSkill = mainSkill;
		_subSkill = subSkill;
	}
	
	public Map<Integer, List<SkillHolder>> getMainSkills()
	{
		return _mainSkill;
	}
	
	public Map<Integer, List<SkillHolder>> getSubSkills()
	{
		return _subSkill;
	}
	
	public List<SkillHolder> getMainSkills(int enchantLevel)
	{
		if (!_mainSkill.containsKey(enchantLevel))
		{
			return Collections.emptyList();
		}
		return _mainSkill.get(enchantLevel);
	}
	
	public List<SkillHolder> getSubSkills(int enchantLevel)
	{
		if (!_subSkill.containsKey(enchantLevel))
		{
			return Collections.emptyList();
		}
		return _subSkill.get(enchantLevel);
	}
}
