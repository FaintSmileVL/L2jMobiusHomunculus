package org.l2jmobius.gameserver.model.holders;

/**
 * @author NviX
 */
public class TeleportListHolder
{
	private final int _locId;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _price;
	
	public TeleportListHolder(int locId, int x, int y, int z, int price)
	{
		_locId = locId;
		_x = x;
		_y = y;
		_z = z;
		_price = price;
	}
	
	public int getLocId()
	{
		return _locId;
	}
	
	public int getX()
	{
		return _x;
	}
	
	public int getY()
	{
		return _y;
	}
	
	public int getZ()
	{
		return _z;
	}
	
	public int getPrice()
	{
		return _price;
	}
}