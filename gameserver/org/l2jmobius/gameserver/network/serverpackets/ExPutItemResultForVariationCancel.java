package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExPutItemResultForVariationCancel implements IClientOutgoingPacket
{
	private final int _itemObjId;
	private final int _itemId;
	private final int _itemAug1;
	private final int _itemAug2;
	private final long _price;
	
	public ExPutItemResultForVariationCancel(ItemInstance item, long price)
	{
		_itemObjId = item.getObjectId();
		_itemId = item.getDisplayId();
		_price = price;
		_itemAug1 = item.getAugmentation().getOption1Id();
		_itemAug2 = item.getAugmentation().getOption2Id();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_ITEM_RESULT_FOR_VARIATION_CANCEL.writeId(packet);
		
		packet.writeD(_itemObjId);
		packet.writeD(_itemId);
		packet.writeD(_itemAug1);
		packet.writeD(_itemAug2);
		packet.writeQ(_price);
		packet.writeD(0x01);
		return true;
	}
}
