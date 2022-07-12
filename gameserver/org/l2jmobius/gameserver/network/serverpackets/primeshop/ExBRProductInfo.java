package org.l2jmobius.gameserver.network.serverpackets.primeshop;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.primeshop.PrimeShopGroup;
import org.l2jmobius.gameserver.model.primeshop.PrimeShopItem;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Gnacik
 */
public class ExBRProductInfo implements IClientOutgoingPacket
{
	private final PrimeShopGroup _item;
	private final int _charPoints;
	private final long _charAdena;
	private final long _charCoins;
	
	public ExBRProductInfo(PrimeShopGroup item, PlayerInstance player)
	{
		_item = item;
		_charPoints = player.getPrimePoints();
		_charAdena = player.getAdena();
		_charCoins = player.getInventory().getInventoryItemCount(23805, -1);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BR_PRODUCT_INFO.writeId(packet);
		
		packet.writeD(_item.getBrId());
		packet.writeD(_item.getPrice());
		packet.writeD(_item.getItems().size());
		for (PrimeShopItem item : _item.getItems())
		{
			packet.writeD(item.getId());
			packet.writeD((int) item.getCount());
			packet.writeD(item.getWeight());
			packet.writeD(item.isTradable());
		}
		packet.writeQ(_charAdena);
		packet.writeQ(_charPoints);
		packet.writeQ(_charCoins); // Hero coins
		return true;
	}
}
