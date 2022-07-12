package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class FriendAddRequest implements IClientOutgoingPacket
{
	private final String _requestorName;
	
	/**
	 * @param requestorName
	 */
	public FriendAddRequest(String requestorName)
	{
		_requestorName = requestorName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.FRIEND_ADD_REQUEST.writeId(packet);
		
		packet.writeC(0x01);
		packet.writeS(_requestorName);
		return true;
	}
}
