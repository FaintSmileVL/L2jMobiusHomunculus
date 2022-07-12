package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExPrivateStoreSellingResult implements IClientOutgoingPacket
{
	private final int _objectId;
	private final long _count;
	private final String _buyer;
	
	public ExPrivateStoreSellingResult(int objectId, long count, String buyer)
	{
		_objectId = objectId;
		_count = count;
		_buyer = buyer;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PRIVATE_STORE_SELLING_RESULT.writeId(packet);
		packet.writeD(_objectId);
		packet.writeQ(_count);
		packet.writeS(_buyer);
		return true;
	}
}