package org.l2jmobius.gameserver.model.itemcontainer;

import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

public class PetInventory extends Inventory
{
	private final PetInstance _owner;
	
	public PetInventory(PetInstance owner)
	{
		_owner = owner;
	}
	
	@Override
	public PetInstance getOwner()
	{
		return _owner;
	}
	
	@Override
	public int getOwnerId()
	{
		// gets the PlayerInstance-owner's ID
		int id;
		try
		{
			id = _owner.getOwner().getObjectId();
		}
		catch (NullPointerException e)
		{
			return 0;
		}
		return id;
	}
	
	/**
	 * Refresh the weight of equipment loaded
	 */
	@Override
	protected void refreshWeight()
	{
		super.refreshWeight();
		_owner.updateAndBroadcastStatus(1);
	}
	
	public boolean validateCapacity(ItemInstance item)
	{
		int slots = 0;
		if (!(item.isStackable() && (getItemByItemId(item.getId()) != null)) && !item.getItem().hasExImmediateEffect())
		{
			slots++;
		}
		return validateCapacity(slots);
	}
	
	@Override
	public boolean validateCapacity(long slots)
	{
		return ((_items.size() + slots) <= _owner.getInventoryLimit());
	}
	
	public boolean validateWeight(ItemInstance item, long count)
	{
		int weight = 0;
		final Item template = ItemTable.getInstance().getTemplate(item.getId());
		if (template == null)
		{
			return false;
		}
		weight += count * template.getWeight();
		return validateWeight(weight);
	}
	
	@Override
	public boolean validateWeight(long weight)
	{
		return ((_totalWeight + weight) <= _owner.getMaxLoad());
	}
	
	@Override
	protected ItemLocation getBaseLocation()
	{
		return ItemLocation.PET;
	}
	
	@Override
	protected ItemLocation getEquipLocation()
	{
		return ItemLocation.PET_EQUIP;
	}
	
	@Override
	public void restore()
	{
		super.restore();
		// check for equipped items from other pets
		for (ItemInstance item : _items.values())
		{
			if (item.isEquipped() && !item.getItem().checkCondition(_owner, _owner, false))
			{
				unEquipItemInSlot(item.getLocationSlot());
			}
		}
	}
	
	public void transferItemsToOwner()
	{
		for (ItemInstance item : _items.values())
		{
			getOwner().transferItem("return", item.getObjectId(), item.getCount(), getOwner().getOwner().getInventory(), getOwner().getOwner(), getOwner());
		}
	}
}
