package org.l2jmobius.gameserver.model.skills;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mobius
 */
public class MountEnabledSkillList
{
	private static final List<Integer> ENABLED_SKILLS = new ArrayList<>(2);
	static
	{
		ENABLED_SKILLS.add(4289); // Wyvern Breath
		ENABLED_SKILLS.add(325); // Strider Siege Assault
	}
	
	public static boolean contains(int skillId)
	{
		return ENABLED_SKILLS.contains(skillId);
	}
}
