package org.l2jmobius.gameserver.enums;

/**
 * @author Nik
 */
public enum SpecialItemType
{
	PC_CAFE_POINTS(-100),
	CLAN_REPUTATION(-200),
	FAME(-300),
	FIELD_CYCLE_POINTS(-400),
	RAIDBOSS_POINTS(-500);
	
	private int _clientId;
	
	private SpecialItemType(int clientId)
	{
		_clientId = clientId;
	}
	
	public int getClientId()
	{
		return _clientId;
	}
	
	public static SpecialItemType getByClientId(int clientId)
	{
		for (SpecialItemType type : values())
		{
			if (type.getClientId() == clientId)
			{
				return type;
			}
		}
		
		return null;
	}
}
