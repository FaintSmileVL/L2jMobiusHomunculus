package org.l2jmobius.gameserver.model.actor.request;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class CompoundRequest extends AbstractRequest
{
	private int _itemOne;
	private int _itemTwo;
	
	public CompoundRequest(PlayerInstance player)
	{
		super(player);
	}
	
	public ItemInstance getItemOne()
	{
		return getActiveChar().getInventory().getItemByObjectId(_itemOne);
	}
	
	public void setItemOne(int itemOne)
	{
		_itemOne = itemOne;
	}
	
	public ItemInstance getItemTwo()
	{
		return getActiveChar().getInventory().getItemByObjectId(_itemTwo);
	}
	
	public void setItemTwo(int itemTwo)
	{
		_itemTwo = itemTwo;
	}
	
	@Override
	public boolean isItemRequest()
	{
		return true;
	}
	
	@Override
	public boolean canWorkWith(AbstractRequest request)
	{
		return !request.isItemRequest();
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		return (objectId > 0) && ((objectId == _itemOne) || (objectId == _itemTwo));
	}
}
