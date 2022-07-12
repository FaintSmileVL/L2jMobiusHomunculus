package org.l2jmobius.gameserver.enums;

import org.l2jmobius.gameserver.model.interfaces.IUpdateTypeComponent;

/**
 * @author UnAfraid
 */
public enum ItemListType implements IUpdateTypeComponent
{
	AUGMENT_BONUS(0x01),
	ELEMENTAL_ATTRIBUTE(0x02),
	ENCHANT_EFFECT(0x04),
	VISUAL_ID(0x08),
	SOUL_CRYSTAL(0x10);
	
	private final int _mask;
	
	private ItemListType(int mask)
	{
		_mask = mask;
	}
	
	@Override
	public int getMask()
	{
		return _mask;
	}
}
