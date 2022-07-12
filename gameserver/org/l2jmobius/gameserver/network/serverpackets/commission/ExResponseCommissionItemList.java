package org.l2jmobius.gameserver.network.serverpackets.commission;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author NosBit
 */
public class ExResponseCommissionItemList extends AbstractItemPacket
{
	private final int _sendType;
	private final Collection<ItemInstance> _items;
	
	public ExResponseCommissionItemList(int sendType, Collection<ItemInstance> items)
	{
		_sendType = sendType;
		_items = items;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_COMMISSION_ITEM_LIST.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_items.size());
			packet.writeD(_items.size());
			for (ItemInstance itemInstance : _items)
			{
				writeItem(packet, itemInstance);
			}
		}
		else
		{
			packet.writeD(0);
			packet.writeD(0);
		}
		return true;
	}
}
