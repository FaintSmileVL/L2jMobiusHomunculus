package org.l2jmobius.gameserver.model.itemauction;

import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.instancemanager.IdManager;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author Forsaiken
 */
public class AuctionItem
{
	private final int _auctionItemId;
	private final int _auctionLength;
	private final long _auctionInitBid;
	
	private final int _itemId;
	private final long _itemCount;
	@SuppressWarnings("unused")
	private final StatSet _itemExtra;
	
	public AuctionItem(int auctionItemId, int auctionLength, long auctionInitBid, int itemId, long itemCount, StatSet itemExtra)
	{
		_auctionItemId = auctionItemId;
		_auctionLength = auctionLength;
		_auctionInitBid = auctionInitBid;
		_itemId = itemId;
		_itemCount = itemCount;
		_itemExtra = itemExtra;
	}
	
	public boolean checkItemExists()
	{
		return ItemTable.getInstance().getTemplate(_itemId) != null;
	}
	
	public int getAuctionItemId()
	{
		return _auctionItemId;
	}
	
	public int getAuctionLength()
	{
		return _auctionLength;
	}
	
	public long getAuctionInitBid()
	{
		return _auctionInitBid;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public long getItemCount()
	{
		return _itemCount;
	}
	
	public ItemInstance createNewItemInstance()
	{
		final ItemInstance item = new ItemInstance(IdManager.getInstance().getNextId(), _itemId);
		World.getInstance().addObject(item);
		item.setCount(_itemCount);
		item.setEnchantLevel(item.getItem().getDefaultEnchantLevel());
		return item;
	}
}