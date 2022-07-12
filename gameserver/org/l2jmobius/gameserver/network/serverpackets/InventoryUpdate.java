package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.ItemInfo;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Advi, UnAfraid
 */
public class InventoryUpdate extends AbstractInventoryUpdate
{
	public InventoryUpdate()
	{
	}
	
	public InventoryUpdate(ItemInstance item)
	{
		super(item);
	}
	
	public InventoryUpdate(List<ItemInfo> items)
	{
		super(items);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.INVENTORY_UPDATE.writeId(packet);
		
		writeItems(packet);
		return true;
	}
}
