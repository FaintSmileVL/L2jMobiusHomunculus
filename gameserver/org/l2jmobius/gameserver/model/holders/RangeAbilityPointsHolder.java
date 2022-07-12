package org.l2jmobius.gameserver.model.holders;

/**
 * @author UnAfraid
 */
public class RangeAbilityPointsHolder
{
	private final int _min;
	private final int _max;
	private final long _sp;
	
	public RangeAbilityPointsHolder(int min, int max, long sp)
	{
		_min = min;
		_max = max;
		_sp = sp;
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
	 * @return the SP.
	 */
	public long getSP()
	{
		return _sp;
	}
}
