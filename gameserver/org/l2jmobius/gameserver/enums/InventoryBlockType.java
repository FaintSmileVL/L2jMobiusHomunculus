package org.l2jmobius.gameserver.enums;

/**
 * @author UnAfraid
 */
public enum InventoryBlockType
{
	NONE(-1),
	BLACKLIST(0),
	WHITELIST(1);
	
	private int _clientId;
	
	private InventoryBlockType(int clientId)
	{
		_clientId = clientId;
	}
	
	public int getClientId()
	{
		return _clientId;
	}
}
