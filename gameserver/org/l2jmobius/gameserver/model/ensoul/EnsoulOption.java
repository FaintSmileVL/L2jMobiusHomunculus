package org.l2jmobius.gameserver.model.ensoul;

import org.l2jmobius.gameserver.model.holders.SkillHolder;

/**
 * @author UnAfraid
 */
public class EnsoulOption extends SkillHolder
{
	private final int _id;
	private final String _name;
	private final String _desc;
	
	public EnsoulOption(int id, String name, String desc, int skillId, int skillLevel)
	{
		super(skillId, skillLevel);
		_id = id;
		_name = name;
		_desc = desc;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getDesc()
	{
		return _desc;
	}
	
	@Override
	public String toString()
	{
		return "Ensoul Id: " + _id + " Name: " + _name + " Desc: " + _desc;
	}
}
