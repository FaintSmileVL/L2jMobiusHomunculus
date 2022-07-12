package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class JoinParty implements IClientOutgoingPacket
{
	private final int _response;
	
	public JoinParty(int response)
	{
		_response = response;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.JOIN_PARTY.writeId(packet);
		
		packet.writeD(_response);
		packet.writeD(0x00); // TODO: Find me!
		return true;
	}
}
