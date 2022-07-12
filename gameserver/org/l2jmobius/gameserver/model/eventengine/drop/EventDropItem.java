package org.l2jmobius.gameserver.model.eventengine.drop;

/**
 * @author UnAfraid
 */
public class EventDropItem
{
	private final int _id;
	private final int _min;
	private final int _max;
	private final double _chance;
	
	public EventDropItem(int id, int min, int max, double chance)
	{
		_id = id;
		_min = min;
		_max = max;
		_chance = chance;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getMin()
	{
		return _min;
	}
	
	public int getMax()
	{
		return _max;
	}
	
	public double getChance()
	{
		return _chance;
	}
}
