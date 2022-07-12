package org.l2jmobius.gameserver.enums;

/**
 * @author St3eT
 */
public enum ClanHallType
{
	AUCTIONABLE(0),
	SIEGEABLE(1),
	OTHER(2);
	
	private final int _clientVal;
	
	private ClanHallType(int clientVal)
	{
		_clientVal = clientVal;
	}
	
	/**
	 * @return the client value.
	 */
	public int getClientVal()
	{
		return _clientVal;
	}
}