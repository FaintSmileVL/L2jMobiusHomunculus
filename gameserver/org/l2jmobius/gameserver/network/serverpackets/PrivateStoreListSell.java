package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.SellBuffsManager;
import org.l2jmobius.gameserver.model.TradeItem;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PrivateStoreListSell extends AbstractItemPacket
{
	private final PlayerInstance _player;
	private final PlayerInstance _seller;
	
	public PrivateStoreListSell(PlayerInstance player, PlayerInstance seller)
	{
		_player = player;
		_seller = seller;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		if (_seller.isSellingBuffs())
		{
			SellBuffsManager.getInstance().sendBuffMenu(_player, _seller, 0);
		}
		else
		{
			OutgoingPackets.PRIVATE_STORE_LIST.writeId(packet);
			
			packet.writeD(_seller.getObjectId());
			packet.writeD(_seller.getSellList().isPackaged() ? 1 : 0);
			packet.writeQ(_player.getAdena());
			packet.writeD(0x00);
			packet.writeD(_seller.getSellList().getItems().length);
			for (TradeItem item : _seller.getSellList().getItems())
			{
				writeItem(packet, item);
				packet.writeQ(item.getPrice());
				packet.writeQ(item.getItem().getReferencePrice() * 2);
			}
		}
		return true;
	}
}
