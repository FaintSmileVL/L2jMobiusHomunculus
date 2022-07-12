package org.l2jmobius.gameserver.model.holders;

/**
 * @author UnAfraid
 */
public class RangeChanceHolder
{
	private final int _min;
	private final int _max;
	private final double _chance;
	
	public RangeChanceHolder(int min, int max, double chance)
	{
		_min = min;
		_max = max;
		_chance = chance;
	}
	
	/**
	 * @return minimum value.
	 */
	public int getMin()
	{
		return _min;
	}
	
	/**
	 * @return maximum value.
	 */
	public int getMax()
	{
		return _max;
	}
	
	/**
	 * @return the chance.
	 */
	public double getChance()
	{
		return _chance;
	}
}
