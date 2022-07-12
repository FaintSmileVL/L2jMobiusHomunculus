package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.MentorManager;
import org.l2jmobius.gameserver.model.PlayerCondOverride;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class TradeStart extends AbstractItemPacket
{
	private final int _sendType;
	private final PlayerInstance _player;
	private final PlayerInstance _partner;
	private final Collection<ItemInstance> _itemList;
	private int _mask = 0;
	
	public TradeStart(int sendType, PlayerInstance player)
	{
		_sendType = sendType;
		_player = player;
		_partner = player.getActiveTradeList().getPartner();
		_itemList = _player.getInventory().getAvailableItems(true, (_player.canOverrideCond(PlayerCondOverride.ITEM_CONDITIONS) && Config.GM_TRADE_RESTRICTED_ITEMS), false);
		if (_partner != null)
		{
			if (player.getFriendList().contains(_partner.getObjectId()))
			{
				_mask |= 0x01;
			}
			if ((player.getClanId() > 0) && (_partner.getClanId() == _partner.getClanId()))
			{
				_mask |= 0x02;
			}
			if ((MentorManager.getInstance().getMentee(player.getObjectId(), _partner.getObjectId()) != null) || (MentorManager.getInstance().getMentee(_partner.getObjectId(), player.getObjectId()) != null))
			{
				_mask |= 0x04;
			}
			if ((player.getAllyId() > 0) && (player.getAllyId() == _partner.getAllyId()))
			{
				_mask |= 0x08;
			}
			
			// Does not shows level
			if (_partner.isGM())
			{
				_mask |= 0x10;
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		if ((_player.getActiveTradeList() == null) || (_partner == null))
		{
			return false;
		}
		
		OutgoingPackets.TRADE_START.writeId(packet);
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_itemList.size());
			packet.writeD(_itemList.size());
			for (ItemInstance item : _itemList)
			{
				writeItem(packet, item);
			}
		}
		else
		{
			packet.writeD(_partner.getObjectId());
			packet.writeC(_mask); // some kind of mask
			if ((_mask & 0x10) == 0)
			{
				packet.writeC(_partner.getLevel());
			}
		}
		return true;
	}
}
