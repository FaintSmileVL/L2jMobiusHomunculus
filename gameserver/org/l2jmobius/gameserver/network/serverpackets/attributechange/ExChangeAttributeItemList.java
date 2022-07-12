package org.l2jmobius.gameserver.network.serverpackets.attributechange;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.ItemInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author Mobius
 */
public class ExChangeAttributeItemList extends AbstractItemPacket
{
	private final ItemInfo[] _itemsList;
	private final int _itemId;
	
	public ExChangeAttributeItemList(int itemId, ItemInfo[] itemsList)
	{
		_itemId = itemId;
		_itemsList = itemsList;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_ATTRIBUTE_ITEM_LIST.writeId(packet);
		packet.writeD(_itemId);
		packet.writeD(_itemsList.length);
		for (ItemInfo item : _itemsList)
		{
			writeItem(packet, item);
		}
		return true;
	}
}
