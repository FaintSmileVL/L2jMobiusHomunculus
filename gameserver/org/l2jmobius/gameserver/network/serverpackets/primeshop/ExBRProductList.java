package org.l2jmobius.gameserver.network.serverpackets.primeshop;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.primeshop.PrimeShopGroup;
import org.l2jmobius.gameserver.model.primeshop.PrimeShopItem;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExBRProductList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _type;
	private final Collection<PrimeShopGroup> _primeList;
	
	public ExBRProductList(PlayerInstance player, int type, Collection<PrimeShopGroup> items)
	{
		_player = player;
		_type = type;
		_primeList = items;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BR_PRODUCT_LIST.writeId(packet);
		
		packet.writeQ(_player.getAdena()); // Adena
		packet.writeQ(0x00); // Hero coins
		packet.writeC(_type); // Type 0 - Home, 1 - History, 2 - Favorites
		packet.writeD(_primeList.size());
		for (PrimeShopGroup brItem : _primeList)
		{
			packet.writeD(brItem.getBrId());
			packet.writeC(brItem.getCat());
			packet.writeC(brItem.getPaymentType()); // Payment Type: 0 - Prime Points, 1 - Adena, 2 - Hero Coins
			packet.writeD(brItem.getPrice());
			packet.writeC(brItem.getPanelType()); // Item Panel Type: 0 - None, 1 - Event, 2 - Sale, 3 - New, 4 - Best
			packet.writeD(brItem.getRecommended()); // Recommended: (bit flags) 1 - Top, 2 - Left, 4 - Right
			packet.writeD(brItem.getStartSale());
			packet.writeD(brItem.getEndSale());
			packet.writeC(brItem.getDaysOfWeek());
			packet.writeC(brItem.getStartHour());
			packet.writeC(brItem.getStartMinute());
			packet.writeC(brItem.getStopHour());
			packet.writeC(brItem.getStopMinute());
			packet.writeD(brItem.getStock());
			packet.writeD(brItem.getTotal());
			packet.writeC(brItem.getSalePercent());
			packet.writeC(brItem.getMinLevel());
			packet.writeC(brItem.getMaxLevel());
			packet.writeD(brItem.getMinBirthday());
			packet.writeD(brItem.getMaxBirthday());
			packet.writeD(brItem.getRestrictionDay());
			packet.writeD(brItem.getAvailableCount());
			packet.writeC(brItem.getItems().size());
			for (PrimeShopItem item : brItem.getItems())
			{
				packet.writeD(item.getId());
				packet.writeD((int) item.getCount());
				packet.writeD(item.getWeight());
				packet.writeD(item.isTradable());
			}
		}
		return true;
	}
}