package org.l2jmobius.gameserver.model.holders;

import java.util.Collection;

/**
 * @author Mobius
 */
public class EventDropHolder extends DropHolder
{
	private final int _minLevel;
	private final int _maxLevel;
	private final Collection<Integer> _monsterIds;
	
	public EventDropHolder(int itemId, long min, long max, double chance, int minLevel, int maxLevel, Collection<Integer> monsterIds)
	{
		super(null, itemId, min, max, chance);
		_minLevel = minLevel;
		_maxLevel = maxLevel;
		_monsterIds = monsterIds;
	}
	
	public int getMinLevel()
	{
		return _minLevel;
	}
	
	public int getMaxLevel()
	{
		return _maxLevel;
	}
	
	public Collection<Integer> getMonsterIds()
	{
		return _monsterIds;
	}
}
