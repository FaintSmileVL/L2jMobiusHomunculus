package org.l2jmobius.gameserver.enums;

/**
 * @author UnAfraid
 */
public enum PlayerAction
{
	ADMIN_COMMAND,
	ADMIN_POINT_PICKING,
	ADMIN_SHOW_TERRITORY,
	MERCENARY_CONFIRM;
	
	private final int _mask;
	
	private PlayerAction()
	{
		_mask = 1 << ordinal();
	}
	
	public int getMask()
	{
		return _mask;
	}
}
