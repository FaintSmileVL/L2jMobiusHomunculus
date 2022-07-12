package org.l2jmobius.gameserver.model.holders;

/**
 * @author Mobius
 */
public class MonsterBookRewardHolder
{
	private final int _kills;
	private final long _exp;
	private final int _sp;
	private final int _points;
	
	public MonsterBookRewardHolder(int kills, long exp, int sp, int points)
	{
		_kills = kills;
		_exp = exp;
		_sp = sp;
		_points = points;
	}
	
	public int getKills()
	{
		return _kills;
	}
	
	public long getExp()
	{
		return _exp;
	}
	
	public int getSp()
	{
		return _sp;
	}
	
	public int getPoints()
	{
		return _points;
	}
}
