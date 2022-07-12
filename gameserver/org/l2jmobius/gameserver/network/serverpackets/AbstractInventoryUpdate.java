package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.ItemInfo;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public abstract class AbstractInventoryUpdate extends AbstractItemPacket
{
	private final Map<Integer, ItemInfo> _items = new ConcurrentSkipListMap<>();
	
	public AbstractInventoryUpdate()
	{
	}
	
	public AbstractInventoryUpdate(ItemInstance item)
	{
		addItem(item);
	}
	
	public AbstractInventoryUpdate(List<ItemInfo> items)
	{
		for (ItemInfo item : items)
		{
			_items.put(item.getObjectId(), item);
		}
	}
	
	public void addItem(ItemInstance item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item));
	}
	
	public void addNewItem(ItemInstance item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item, 1));
	}
	
	public void addModifiedItem(ItemInstance item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item, 2));
	}
	
	public void addRemovedItem(ItemInstance item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item, 3));
	}
	
	public void addItems(List<ItemInstance> items)
	{
		for (ItemInstance item : items)
		{
			_items.put(item.getObjectId(), new ItemInfo(item));
		}
	}
	
	public Collection<ItemInfo> getItems()
	{
		return _items.values();
	}
	
	protected final void writeItems(PacketWriter packet)
	{
		packet.writeC(0); // 140
		packet.writeD(0); // 140
		packet.writeD(_items.size()); // 140
		for (ItemInfo item : _items.values())
		{
			packet.writeH(item.getChange()); // Update type : 01-add, 02-modify, 03-remove
			writeItem(packet, item);
		}
	}
}
