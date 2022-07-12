package org.l2jmobius.gameserver.model.items;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.items.type.ArmorType;

/**
 * This class is dedicated to the management of armors.
 */
public class Armor extends Item
{
	private ArmorType _type;
	
	/**
	 * Constructor for Armor.
	 * @param set the StatSet designating the set of couples (key,value) characterizing the armor.
	 */
	public Armor(StatSet set)
	{
		super(set);
	}
	
	@Override
	public void set(StatSet set)
	{
		super.set(set);
		_type = set.getEnum("armor_type", ArmorType.class, ArmorType.NONE);
		
		final long bodyPart = getBodyPart();
		if ((bodyPart == Item.SLOT_ARTIFACT) || (bodyPart == Item.SLOT_AGATHION))
		{
			_type1 = Item.TYPE1_SHIELD_ARMOR;
			_type2 = Item.TYPE2_ACCESSORY;
		}
		else if ((bodyPart == Item.SLOT_NECK) || ((bodyPart & Item.SLOT_L_EAR) != 0) || ((bodyPart & Item.SLOT_L_FINGER) != 0) || ((bodyPart & Item.SLOT_R_BRACELET) != 0) || ((bodyPart & Item.SLOT_L_BRACELET) != 0) || ((bodyPart & Item.SLOT_ARTIFACT_BOOK) != 0))
		{
			_type1 = Item.TYPE1_WEAPON_RING_EARRING_NECKLACE;
			_type2 = Item.TYPE2_ACCESSORY;
		}
		else
		{
			if ((_type == ArmorType.NONE) && (getBodyPart() == Item.SLOT_L_HAND))
			{
				_type = ArmorType.SHIELD;
			}
			_type1 = Item.TYPE1_SHIELD_ARMOR;
			_type2 = Item.TYPE2_SHIELD_ARMOR;
		}
	}
	
	/**
	 * @return the type of the armor.
	 */
	@Override
	public ArmorType getItemType()
	{
		return _type;
	}
	
	/**
	 * @return the ID of the item after applying the mask.
	 */
	@Override
	public int getItemMask()
	{
		return _type.mask();
	}
	
	/**
	 * @return {@code true} if the item is an armor, {@code false} otherwise
	 */
	@Override
	public boolean isArmor()
	{
		return true;
	}
}
