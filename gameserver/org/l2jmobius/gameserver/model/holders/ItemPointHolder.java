package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.StatSet;

/**
 * @author Sdw
 */
public class ItemPointHolder extends ItemHolder
{
	private final int _points;
	
	public ItemPointHolder(StatSet params)
	{
		this(params.getInt("id"), params.getLong("count"), params.getInt("points"));
	}
	
	public ItemPointHolder(int id, long count, int points)
	{
		super(id, count);
		_points = points;
	}
	
	/**
	 * Gets the point.
	 * @return the number of point to get the item
	 */
	public int getPoints()
	{
		return _points;
	}
	
	@Override
	public String toString()
	{
		return "[" + getClass().getSimpleName() + "] ID: " + getId() + ", count: " + getCount() + ", points: " + _points;
	}
}
