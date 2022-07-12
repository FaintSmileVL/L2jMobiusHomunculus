package org.l2jmobius.gameserver.model.holders;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Mobius
 */
public class ClanMasteryHolder
{
	private final int _id;
	private final List<Skill> _skills = new ArrayList<>();
	private final int _clanLevel;
	private final int _clanReputation;
	private final int _previousMastery;
	private final int _previousMasteryAlt;
	
	public ClanMasteryHolder(int id, Skill skill1, Skill skill2, Skill skill3, Skill skill4, int clanLevel, int clanReputation, int previousMastery, int previousMasteryAlt)
	{
		_id = id;
		_clanLevel = clanLevel;
		_clanReputation = clanReputation;
		_previousMastery = previousMastery;
		_previousMasteryAlt = previousMasteryAlt;
		_skills.add(skill1);
		if (skill2 != null)
		{
			_skills.add(skill2);
		}
		if (skill3 != null)
		{
			_skills.add(skill3);
		}
		if (skill4 != null)
		{
			_skills.add(skill4);
		}
	}
	
	public int getId()
	{
		return _id;
	}
	
	public List<Skill> getSkills()
	{
		return _skills;
	}
	
	public int getClanLevel()
	{
		return _clanLevel;
	}
	
	public int getClanReputation()
	{
		return _clanReputation;
	}
	
	public int getPreviousMastery()
	{
		return _previousMastery;
	}
	
	public int getPreviousMasteryAlt()
	{
		return _previousMasteryAlt;
	}
}
