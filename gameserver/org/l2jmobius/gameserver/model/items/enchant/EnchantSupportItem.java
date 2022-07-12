package org.l2jmobius.gameserver.model.items.enchant;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.items.type.EtcItemType;
import org.l2jmobius.gameserver.model.items.type.ItemType;

/**
 * @author UnAfraid
 */
public class EnchantSupportItem extends AbstractEnchantItem
{
	private final boolean _isWeapon;
	private final boolean _isBlessed;
	private final boolean _isGiant;
	private final ItemType type;
	
	public EnchantSupportItem(StatSet set)
	{
		super(set);
		type = getItem().getItemType();
		_isWeapon = (type == EtcItemType.ENCHT_ATTR_INC_PROP_ENCHT_WP) || (type == EtcItemType.BLESSED_ENCHT_ATTR_INC_PROP_ENCHT_WP) || (type == EtcItemType.GIANT_ENCHT_ATTR_INC_PROP_ENCHT_WP) || (type == EtcItemType.BLESSED_GIANT_ENCHT_ATTR_INC_PROP_ENCHT_WP);
		_isBlessed = (type == EtcItemType.BLESSED_ENCHT_ATTR_INC_PROP_ENCHT_AM) || (type == EtcItemType.BLESSED_ENCHT_ATTR_INC_PROP_ENCHT_WP) || (type == EtcItemType.BLESSED_GIANT_ENCHT_ATTR_INC_PROP_ENCHT_AM) || (type == EtcItemType.BLESSED_GIANT_ENCHT_ATTR_INC_PROP_ENCHT_WP);
		_isGiant = (type == EtcItemType.GIANT_ENCHT_ATTR_INC_PROP_ENCHT_AM) || (type == EtcItemType.GIANT_ENCHT_ATTR_INC_PROP_ENCHT_WP) || (type == EtcItemType.BLESSED_GIANT_ENCHT_ATTR_INC_PROP_ENCHT_AM) || (type == EtcItemType.BLESSED_GIANT_ENCHT_ATTR_INC_PROP_ENCHT_WP);
	}
	
	@Override
	public boolean isWeapon()
	{
		return _isWeapon;
	}
	
	public boolean isBlessed()
	{
		return _isBlessed;
	}
	
	public boolean isGiant()
	{
		return _isGiant;
	}
}
