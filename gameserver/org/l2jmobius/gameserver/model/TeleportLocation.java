package org.l2jmobius.gameserver.model;

/**
 * @version $Revision: 1.2.4.1 $ $Date: 2005/03/27 15:29:32 $
 */
public class TeleportLocation
{
	private int _teleId;
	private int _locX;
	private int _locY;
	private int _locZ;
	private int _price;
	private boolean _forNoble;
	private int _itemId;
	
	/**
	 * @param id
	 */
	public void setTeleId(int id)
	{
		_teleId = id;
	}
	
	/**
	 * @param locX
	 */
	public void setLocX(int locX)
	{
		_locX = locX;
	}
	
	/**
	 * @param locY
	 */
	public void setLocY(int locY)
	{
		_locY = locY;
	}
	
	/**
	 * @param locZ
	 */
	public void setLocZ(int locZ)
	{
		_locZ = locZ;
	}
	
	/**
	 * @param price
	 */
	public void setPrice(int price)
	{
		_price = price;
	}
	
	/**
	 * @param value
	 */
	public void setForNoble(boolean value)
	{
		_forNoble = value;
	}
	
	/**
	 * @param value
	 */
	public void setItemId(int value)
	{
		_itemId = value;
	}
	
	/**
	 * @return
	 */
	public int getTeleId()
	{
		return _teleId;
	}
	
	/**
	 * @return
	 */
	public int getLocX()
	{
		return _locX;
	}
	
	/**
	 * @return
	 */
	public int getLocY()
	{
		return _locY;
	}
	
	/**
	 * @return
	 */
	public int getLocZ()
	{
		return _locZ;
	}
	
	/**
	 * @return
	 */
	public int getPrice()
	{
		return _price;
	}
	
	/**
	 * @return
	 */
	public boolean isForNoble()
	{
		return _forNoble;
	}
	
	/**
	 * @return
	 */
	public int getItemId()
	{
		return _itemId;
	}
}
