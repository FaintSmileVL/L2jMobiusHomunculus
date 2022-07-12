package org.l2jmobius.gameserver.model.punishment;

/**
 * @author UnAfraid
 */
public enum PunishmentAffect
{
	ACCOUNT,
	CHARACTER,
	IP;
	// HWID; Not implemented yet.
	
	public static PunishmentAffect getByName(String name)
	{
		for (PunishmentAffect type : values())
		{
			if (type.name().equalsIgnoreCase(name))
			{
				return type;
			}
		}
		return null;
	}
}
