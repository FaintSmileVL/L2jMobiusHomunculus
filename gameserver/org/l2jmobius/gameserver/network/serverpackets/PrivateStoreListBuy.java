package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.TradeItem;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @version $Revision: 1.7.2.2.2.3 $ $Date: 2005/03/27 15:29:39 $
 */
public class PrivateStoreListBuy extends AbstractItemPacket
{
	private final int _objId;
	private final long _playerAdena;
	private final Collection<TradeItem> _items;
	
	public PrivateStoreListBuy(PlayerInstance player, PlayerInstance storePlayer)
	{
		_objId = storePlayer.getObjectId();
		_playerAdena = player.getAdena();
		storePlayer.getSellList().updateItems(); // Update SellList for case inventory content has changed
		_items = storePlayer.getBuyList().getAvailableItems(player.getInventory());
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PRIVATE_STORE_BUY_LIST.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeQ(_playerAdena);
		packet.writeD(0x00); // Viewer's item count?
		packet.writeD(_items.size());
		
		int slotNumber = 0;
		for (TradeItem item : _items)
		{
			slotNumber++;
			writeItem(packet, item);
			packet.writeD(slotNumber); // Slot in shop
			packet.writeQ(item.getPrice());
			packet.writeQ(item.getItem().getReferencePrice() * 2);
			packet.writeQ(item.getStoreCount());
		}
		return true;
	}
}
