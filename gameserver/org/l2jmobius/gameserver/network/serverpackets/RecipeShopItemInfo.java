package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class RecipeShopItemInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _manufacturer;
	private final int _recipeId;
	private final Boolean _success;
	private final long _manufacturePrice;
	private final long _offeringMaximumAdena;
	
	public RecipeShopItemInfo(PlayerInstance manufacturer, int recipeId, boolean success, long manufacturePrice, long offeringMaximumAdena)
	{
		_manufacturer = manufacturer;
		_recipeId = recipeId;
		_success = success;
		_manufacturePrice = manufacturePrice;
		_offeringMaximumAdena = offeringMaximumAdena;
	}
	
	public RecipeShopItemInfo(PlayerInstance manufacturer, int recipeId, long manufacturePrice, long offeringMaximumAdena)
	{
		_manufacturer = manufacturer;
		_recipeId = recipeId;
		_success = null;
		_manufacturePrice = manufacturePrice;
		_offeringMaximumAdena = offeringMaximumAdena;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.RECIPE_SHOP_ITEM_INFO.writeId(packet);
		
		packet.writeD(_manufacturer.getObjectId());
		packet.writeD(_recipeId);
		packet.writeD((int) _manufacturer.getCurrentMp());
		packet.writeD(_manufacturer.getMaxMp());
		packet.writeD(_success == null ? -1 : (_success ? 1 : 0)); // item creation none/success/failed
		packet.writeQ(_manufacturePrice);
		packet.writeC(_offeringMaximumAdena > 0 ? 1 : 0); // Trigger offering window if 1
		packet.writeQ(_offeringMaximumAdena);
		return true;
	}
}
