package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class GMViewWarehouseWithdrawList extends AbstractItemPacket
{
	private final int _sendType;
	private final Collection<ItemInstance> _items;
	private final String _playerName;
	private final long _money;
	
	public GMViewWarehouseWithdrawList(int sendType, PlayerInstance player)
	{
		_sendType = sendType;
		_items = player.getWarehouse().getItems();
		_playerName = player.getName();
		_money = player.getWarehouse().getAdena();
	}
	
	public GMViewWarehouseWithdrawList(int sendType, Clan clan)
	{
		_sendType = sendType;
		_playerName = clan.getLeaderName();
		_items = clan.getWarehouse().getItems();
		_money = clan.getWarehouse().getAdena();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GM_VIEW_WAREHOUSE_WITHDRAW_LIST.writeId(packet);
		
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_items.size());
			packet.writeD(_items.size());
			for (ItemInstance item : _items)
			{
				writeItem(packet, item);
				packet.writeD(item.getObjectId());
			}
		}
		else
		{
			packet.writeS(_playerName);
			packet.writeQ(_money);
			packet.writeD(_items.size());
		}
		return true;
	}
}
