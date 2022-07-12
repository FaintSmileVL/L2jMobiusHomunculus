package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class WareHouseWithdrawalList extends AbstractItemPacket
{
	public static final int PRIVATE = 1;
	public static final int CLAN = 2;
	public static final int CASTLE = 3; // not sure
	public static final int FREIGHT = 1;
	private final int _sendType;
	private PlayerInstance _player;
	private long _playerAdena;
	private final int _invSize;
	private Collection<ItemInstance> _items;
	private final List<Integer> _itemsStackable = new ArrayList<>();
	/**
	 * <ul>
	 * <li>0x01-Private Warehouse</li>
	 * <li>0x02-Clan Warehouse</li>
	 * <li>0x03-Castle Warehouse</li>
	 * <li>0x04-Warehouse</li>
	 * </ul>
	 */
	private int _whType;
	
	public WareHouseWithdrawalList(int sendType, PlayerInstance player, int type)
	{
		_sendType = sendType;
		_player = player;
		_whType = type;
		_playerAdena = _player.getAdena();
		_invSize = player.getInventory().getSize();
		if (_player.getActiveWarehouse() == null)
		{
			LOGGER.warning("error while sending withdraw request to: " + _player.getName());
			return;
		}
		
		_items = _player.getActiveWarehouse().getItems();
		for (ItemInstance item : _items)
		{
			if (item.isStackable())
			{
				_itemsStackable.add(item.getDisplayId());
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.WAREHOUSE_WITHDRAW_LIST.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeH(0x00);
			packet.writeD(_invSize);
			packet.writeD(_items.size());
			for (ItemInstance item : _items)
			{
				writeItem(packet, item);
				packet.writeD(item.getObjectId());
				packet.writeD(0x00);
				packet.writeD(0x00);
			}
		}
		else
		{
			packet.writeH(_whType);
			packet.writeQ(_playerAdena);
			packet.writeD(_invSize);
			packet.writeD(_items.size());
		}
		return true;
	}
}
