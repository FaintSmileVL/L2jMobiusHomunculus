package org.l2jmobius.gameserver.enums;

/**
 * @author Sdw
 */
public enum LuckyGameItemType
{
	COMMON(1),
	UNIQUE(2),
	RARE(3);
	
	private final int _clientId;
	
	LuckyGameItemType(int clientId)
	{
		_clientId = clientId;
	}
	
	public int getClientId()
	{
		return _clientId;
	}
}
