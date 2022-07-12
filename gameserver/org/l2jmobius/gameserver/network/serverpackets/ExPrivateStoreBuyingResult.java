package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExPrivateStoreBuyingResult implements IClientOutgoingPacket
{
	private final int _objectId;
	private final long _count;
	private final String _seller;
	
	public ExPrivateStoreBuyingResult(int objectId, long count, String seller)
	{
		_objectId = objectId;
		_count = count;
		_seller = seller;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PRIVATE_STORE_BUYING_RESULT.writeId(packet);
		packet.writeD(_objectId);
		packet.writeQ(_count);
		packet.writeS(_seller);
		return true;
	}
}