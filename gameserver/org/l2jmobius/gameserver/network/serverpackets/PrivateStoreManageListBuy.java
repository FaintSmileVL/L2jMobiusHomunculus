package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.TradeItem;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PrivateStoreManageListBuy extends AbstractItemPacket
{
	private final int _sendType;
	private final int _objId;
	private final long _playerAdena;
	private final Collection<ItemInstance> _itemList;
	private final TradeItem[] _buyList;
	
	public PrivateStoreManageListBuy(int sendType, PlayerInstance player)
	{
		_sendType = sendType;
		_objId = player.getObjectId();
		_playerAdena = player.getAdena();
		_itemList = player.getInventory().getUniqueItems(false, true);
		_buyList = player.getBuyList().getItems();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PRIVATE_STORE_BUY_MANAGE_LIST.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_itemList.size());
			packet.writeD(_itemList.size());
			for (ItemInstance item : _itemList)
			{
				writeItem(packet, item);
				packet.writeQ(item.getItem().getReferencePrice() * 2);
			}
		}
		else
		{
			packet.writeD(_objId);
			packet.writeQ(_playerAdena);
			packet.writeD(0x00);
			for (ItemInstance item : _itemList)
			{
				writeItem(packet, item);
				packet.writeQ(item.getItem().getReferencePrice() * 2);
			}
			packet.writeD(0x00);
			for (TradeItem item2 : _buyList)
			{
				writeItem(packet, item2);
				packet.writeQ(item2.getPrice());
				packet.writeQ(item2.getItem().getReferencePrice() * 2);
				packet.writeQ(item2.getCount());
			}
		}
		return true;
	}
}
