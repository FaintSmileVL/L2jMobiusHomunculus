package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class ExClosePartyRoom implements IClientOutgoingPacket
{
	public static final ExClosePartyRoom STATIC_PACKET = new ExClosePartyRoom();
	
	private ExClosePartyRoom()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CLOSE_PARTY_ROOM.writeId(packet);
		
		return true;
	}
}