package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ChooseInventoryItem implements IClientOutgoingPacket
{
	private final int _itemId;
	
	public ChooseInventoryItem(int itemId)
	{
		_itemId = itemId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHOOSE_INVENTORY_ITEM.writeId(packet);
		
		packet.writeD(_itemId);
		return true;
	}
}
