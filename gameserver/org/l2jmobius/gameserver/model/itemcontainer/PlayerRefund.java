package org.l2jmobius.gameserver.model.itemcontainer;

import java.util.logging.Level;

import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author DS
 */
public class PlayerRefund extends ItemContainer
{
	private final PlayerInstance _owner;
	
	public PlayerRefund(PlayerInstance owner)
	{
		_owner = owner;
	}
	
	@Override
	public String getName()
	{
		return "Refund";
	}
	
	@Override
	public PlayerInstance getOwner()
	{
		return _owner;
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.REFUND;
	}
	
	@Override
	protected void addItem(ItemInstance item)
	{
		super.addItem(item);
		try
		{
			if (getSize() > 12)
			{
				final ItemInstance removedItem = _items.remove(0);
				if (removedItem != null)
				{
					ItemTable.getInstance().destroyItem("ClearRefund", removedItem, getOwner(), null);
					removedItem.updateDatabase(true);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "addItem()", e);
		}
	}
	
	@Override
	public void refreshWeight()
	{
	}
	
	@Override
	public void deleteMe()
	{
		try
		{
			for (ItemInstance item : _items.values())
			{
				ItemTable.getInstance().destroyItem("ClearRefund", item, getOwner(), null);
				item.updateDatabase(true);
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "deleteMe()", e);
		}
		_items.clear();
	}
	
	@Override
	public void restore()
	{
	}
}