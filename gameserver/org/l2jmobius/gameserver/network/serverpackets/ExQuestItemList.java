package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExQuestItemList extends AbstractItemPacket
{
	private final int _sendType;
	private final PlayerInstance _player;
	private final Collection<ItemInstance> _items;
	
	public ExQuestItemList(int sendType, PlayerInstance player)
	{
		_sendType = sendType;
		_player = player;
		_items = player.getInventory().getItems(ItemInstance::isQuestItem);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_QUEST_ITEM_LIST.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_items.size());
		}
		else
		{
			packet.writeH(0);
		}
		packet.writeD(_items.size());
		for (ItemInstance item : _items)
		{
			writeItem(packet, item);
		}
		writeInventoryBlock(packet, _player.getInventory());
		return true;
	}
}
