package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;
import java.util.stream.Collectors;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ItemList extends AbstractItemPacket
{
	private final int _sendType;
	private final PlayerInstance _player;
	private final List<ItemInstance> _items;
	
	public ItemList(int sendType, PlayerInstance player)
	{
		_sendType = sendType;
		_player = player;
		_items = player.getInventory().getItems(item -> !item.isQuestItem()).stream().collect(Collectors.toList());
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ITEM_LIST.writeId(packet);
		if (_sendType == 2)
		{
			packet.writeC(_sendType);
			packet.writeD(_items.size());
			packet.writeD(_items.size());
			for (ItemInstance item : _items)
			{
				writeItem(packet, item);
			}
		}
		else
		{
			packet.writeC(0x01); // _showWindow ? 0x01 : 0x00
			packet.writeD(0x00);
			packet.writeD(_items.size());
		}
		writeInventoryBlock(packet, _player.getInventory());
		return true;
	}
}
