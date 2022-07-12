package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Opens the CommandChannel Information window
 * @author chris_00
 */
public class ExOpenMPCC implements IClientOutgoingPacket
{
	public static final ExOpenMPCC STATIC_PACKET = new ExOpenMPCC();
	
	private ExOpenMPCC()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_OPEN_MPCC.writeId(packet);
		
		return true;
	}
}
