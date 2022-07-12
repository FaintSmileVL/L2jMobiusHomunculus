package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class RecipeShopSellList implements IClientOutgoingPacket
{
	private final PlayerInstance _buyer;
	private final PlayerInstance _manufacturer;
	
	public RecipeShopSellList(PlayerInstance buyer, PlayerInstance manufacturer)
	{
		_buyer = buyer;
		_manufacturer = manufacturer;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.RECIPE_SHOP_SELL_LIST.writeId(packet);
		
		packet.writeD(_manufacturer.getObjectId());
		packet.writeD((int) _manufacturer.getCurrentMp()); // Creator's MP
		packet.writeD(_manufacturer.getMaxMp()); // Creator's MP
		packet.writeQ(_buyer.getAdena()); // Buyer Adena
		if (!_manufacturer.hasManufactureShop())
		{
			packet.writeD(0x00);
		}
		else
		{
			packet.writeD(_manufacturer.getManufactureItems().size());
			for (Entry<Integer, Long> item : _manufacturer.getManufactureItems().entrySet())
			{
				packet.writeD(item.getKey());
				packet.writeD(0x00); // CanCreate?
				packet.writeQ(item.getValue());
			}
		}
		return true;
	}
}
