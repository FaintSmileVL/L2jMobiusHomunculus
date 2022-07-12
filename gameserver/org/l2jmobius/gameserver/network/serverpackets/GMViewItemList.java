package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class GMViewItemList extends AbstractItemPacket
{
	private final int _sendType;
	private final List<ItemInstance> _items = new ArrayList<>();
	private final int _limit;
	private final String _playerName;
	
	public GMViewItemList(int sendType, PlayerInstance player)
	{
		_sendType = sendType;
		_playerName = player.getName();
		_limit = player.getInventoryLimit();
		for (ItemInstance item : player.getInventory().getItems())
		{
			_items.add(item);
		}
	}
	
	public GMViewItemList(int sendType, PetInstance cha)
	{
		_sendType = sendType;
		_playerName = cha.getName();
		_limit = cha.getInventoryLimit();
		for (ItemInstance item : cha.getInventory().getItems())
		{
			_items.add(item);
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GM_VIEW_ITEM_LIST.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_items.size());
		}
		else
		{
			packet.writeS(_playerName);
			packet.writeD(_limit); // inventory limit
		}
		packet.writeD(_items.size());
		for (ItemInstance item : _items)
		{
			writeItem(packet, item);
		}
		return true;
	}
}
