package org.l2jmobius.gameserver.enums;

/**
 * @author Sdw
 */
public enum LuckyGameResultType
{
	INVALID_CAPACITY(-2),
	INVALID_ITEM_COUNT(-1),
	DISABLED(0),
	SUCCESS(1);
	
	private final int _clientId;
	
	private LuckyGameResultType(int clientId)
	{
		_clientId = clientId;
	}
	
	public int getClientId()
	{
		return _clientId;
	}
}
