package org.l2jmobius.gameserver.model.punishment;

/**
 * @author UnAfraid
 */
public enum PunishmentType
{
	BAN,
	CHAT_BAN,
	PARTY_BAN,
	JAIL,
	COC_BAN;
	
	public static PunishmentType getByName(String name)
	{
		for (PunishmentType type : values())
		{
			if (type.name().equalsIgnoreCase(name))
			{
				return type;
			}
		}
		return null;
	}
}
