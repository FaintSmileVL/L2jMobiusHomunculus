package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class AskJoinAlly implements IClientOutgoingPacket
{
	private final String _requestorName;
	private final int _requestorObjId;
	
	/**
	 * @param requestorObjId
	 * @param requestorName
	 */
	public AskJoinAlly(int requestorObjId, String requestorName)
	{
		_requestorName = requestorName;
		_requestorObjId = requestorObjId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ASK_JOIN_ALLIANCE.writeId(packet);
		
		packet.writeD(_requestorObjId);
		packet.writeS(null); // Ally Name ?
		packet.writeS(null); // TODO: Find me!
		packet.writeS(_requestorName);
		return true;
	}
}
