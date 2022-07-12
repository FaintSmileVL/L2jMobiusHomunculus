package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 ** @author Gnacik
 */
public class ShopPreviewInfo implements IClientOutgoingPacket
{
	private final Map<Integer, Integer> _itemlist;
	
	public ShopPreviewInfo(Map<Integer, Integer> itemlist)
	{
		_itemlist = itemlist;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOP_PREVIEW_INFO.writeId(packet);
		
		packet.writeD(Inventory.PAPERDOLL_TOTALSLOTS);
		// Slots
		packet.writeD(getFromList(Inventory.PAPERDOLL_UNDER));
		packet.writeD(getFromList(Inventory.PAPERDOLL_REAR));
		packet.writeD(getFromList(Inventory.PAPERDOLL_LEAR));
		packet.writeD(getFromList(Inventory.PAPERDOLL_NECK));
		packet.writeD(getFromList(Inventory.PAPERDOLL_RFINGER));
		packet.writeD(getFromList(Inventory.PAPERDOLL_LFINGER));
		packet.writeD(getFromList(Inventory.PAPERDOLL_HEAD));
		packet.writeD(getFromList(Inventory.PAPERDOLL_RHAND));
		packet.writeD(getFromList(Inventory.PAPERDOLL_LHAND));
		packet.writeD(getFromList(Inventory.PAPERDOLL_GLOVES));
		packet.writeD(getFromList(Inventory.PAPERDOLL_CHEST));
		packet.writeD(getFromList(Inventory.PAPERDOLL_LEGS));
		packet.writeD(getFromList(Inventory.PAPERDOLL_FEET));
		packet.writeD(getFromList(Inventory.PAPERDOLL_CLOAK));
		packet.writeD(getFromList(Inventory.PAPERDOLL_RHAND));
		packet.writeD(getFromList(Inventory.PAPERDOLL_HAIR));
		packet.writeD(getFromList(Inventory.PAPERDOLL_HAIR2));
		packet.writeD(getFromList(Inventory.PAPERDOLL_RBRACELET));
		packet.writeD(getFromList(Inventory.PAPERDOLL_LBRACELET));
		return true;
	}
	
	private int getFromList(int key)
	{
		return (_itemlist.containsKey(key) ? _itemlist.get(key) : 0);
	}
}