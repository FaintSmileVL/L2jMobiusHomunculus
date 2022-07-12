package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.TradeItem;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PrivateStoreManageListSell extends AbstractItemPacket
{
	private final int _sendType;
	private final int _objId;
	private final long _playerAdena;
	private final boolean _packageSale;
	private final Collection<TradeItem> _itemList;
	private final TradeItem[] _sellList;
	
	public PrivateStoreManageListSell(int sendType, PlayerInstance player, boolean isPackageSale)
	{
		_sendType = sendType;
		_objId = player.getObjectId();
		_playerAdena = player.getAdena();
		player.getSellList().updateItems();
		_packageSale = isPackageSale;
		_itemList = player.getInventory().getAvailableItems(player.getSellList());
		_sellList = player.getSellList().getItems();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PRIVATE_STORE_MANAGE_LIST.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_itemList.size());
			packet.writeD(_itemList.size());
			for (TradeItem item : _itemList)
			{
				writeItem(packet, item);
				packet.writeQ(item.getItem().getReferencePrice() * 2);
			}
		}
		else
		{
			packet.writeD(_objId);
			packet.writeD(_packageSale ? 1 : 0);
			packet.writeQ(_playerAdena);
			packet.writeD(0x00);
			for (TradeItem item : _itemList)
			{
				writeItem(packet, item);
				packet.writeQ(item.getItem().getReferencePrice() * 2);
			}
			packet.writeD(0x00);
			for (TradeItem item2 : _sellList)
			{
				writeItem(packet, item2);
				packet.writeQ(item2.getPrice());
				packet.writeQ(item2.getItem().getReferencePrice() * 2);
			}
		}
		return true;
	}
}
