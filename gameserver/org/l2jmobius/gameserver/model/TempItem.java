package org.l2jmobius.gameserver.model;

import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * Class explanation:<br>
 * For item counting or checking purposes. When you don't want to modify inventory<br>
 * class contains itemId, quantity, ownerId, referencePrice, but not objectId<br>
 * is stored, this will be only "list" of items with it's owner
 */
public class TempItem
{
	private final int _itemId;
	private int _quantity;
	private final long _referencePrice;
	private final String _itemName;
	
	/**
	 * @param item
	 * @param quantity of that item
	 */
	public TempItem(ItemInstance item, int quantity)
	{
		super();
		_itemId = item.getId();
		_quantity = quantity;
		_itemName = item.getItem().getName();
		_referencePrice = item.getReferencePrice();
	}
	
	/**
	 * @return the quantity.
	 */
	public int getQuantity()
	{
		return _quantity;
	}
	
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(int quantity)
	{
		_quantity = quantity;
	}
	
	public long getReferencePrice()
	{
		return _referencePrice;
	}
	
	/**
	 * @return the itemId.
	 */
	public int getItemId()
	{
		return _itemId;
	}
	
	/**
	 * @return the itemName.
	 */
	public String getItemName()
	{
		return _itemName;
	}
}
