package org.l2jmobius.gameserver.enums;

import org.l2jmobius.gameserver.model.items.Armor;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.Weapon;

/**
 * @author Nik
 */
public enum CrystallizationType
{
	NONE,
	WEAPON,
	ARMOR,
	ACCESORY;
	
	public static CrystallizationType getByItem(Item item)
	{
		if (item instanceof Weapon)
		{
			return WEAPON;
		}
		if (item instanceof Armor)
		{
			return ARMOR;
		}
		if ((item.getBodyPart() == Item.SLOT_R_EAR) //
			|| (item.getBodyPart() == Item.SLOT_L_EAR) //
			|| (item.getBodyPart() == Item.SLOT_R_FINGER) //
			|| (item.getBodyPart() == Item.SLOT_L_FINGER) //
			|| (item.getBodyPart() == Item.SLOT_NECK) //
			|| (item.getBodyPart() == Item.SLOT_HAIR) //
			|| (item.getBodyPart() == Item.SLOT_HAIR2) //
			|| (item.getBodyPart() == Item.SLOT_HAIRALL) //
			|| (item.getBodyPart() == Item.SLOT_ARTIFACT_BOOK) //
			|| (item.getBodyPart() == Item.SLOT_ARTIFACT))
		{
			return ACCESORY;
		}
		
		return NONE;
	}
}
