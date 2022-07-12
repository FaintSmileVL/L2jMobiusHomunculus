package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionSlotItemId.
 * @author mkizub
 */
public class ConditionSlotItemId extends ConditionInventory
{
	private final int _itemId;
	private final int _enchantLevel;
	
	/**
	 * Instantiates a new condition slot item id.
	 * @param slot the slot
	 * @param itemId the item id
	 * @param enchantLevel the enchant level
	 */
	public ConditionSlotItemId(int slot, int itemId, int enchantLevel)
	{
		super(slot);
		_itemId = itemId;
		_enchantLevel = enchantLevel;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if ((effector == null) || !effector.isPlayer())
		{
			return false;
		}
		
		final ItemInstance itemSlot = effector.getInventory().getPaperdollItem(_slot);
		if (itemSlot == null)
		{
			return _itemId == 0;
		}
		return (itemSlot.getId() == _itemId) && (itemSlot.getEnchantLevel() >= _enchantLevel);
	}
}
