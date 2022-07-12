package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Close the CommandChannel Information window
 * @author chris_00
 */
public class ExCloseMPCC implements IClientOutgoingPacket
{
	public static final ExCloseMPCC STATIC_PACKET = new ExCloseMPCC();
	
	private ExCloseMPCC()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CLOSE_MPCC.writeId(packet);
		
		return true;
	}
}
