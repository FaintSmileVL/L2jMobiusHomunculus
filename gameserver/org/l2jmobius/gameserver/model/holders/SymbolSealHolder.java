package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author NviX
 */
public class SymbolSealHolder
{
	private final int _symbolId;
	private final Skill _skill;
	
	public SymbolSealHolder(int symbolId, Skill skill)
	{
		_symbolId = symbolId;
		_skill = skill;
	}
	
	public int getSymbolId()
	{
		return _symbolId;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
}
