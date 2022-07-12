package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExPutItemResultForVariationMake implements IClientOutgoingPacket
{
	private final int _itemObjId;
	private final int _itemId;
	
	public ExPutItemResultForVariationMake(int itemObjId, int itemId)
	{
		_itemObjId = itemObjId;
		_itemId = itemId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_ITEM_RESULT_FOR_VARIATION_MAKE.writeId(packet);
		
		packet.writeD(_itemObjId);
		packet.writeD(_itemId);
		packet.writeD(0x01);
		return true;
	}
}
