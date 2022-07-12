package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExDissmissMPCCRoom implements IClientOutgoingPacket
{
	public static final ExDissmissMPCCRoom STATIC_PACKET = new ExDissmissMPCCRoom();
	
	private ExDissmissMPCCRoom()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DISSMISS_MPCC_ROOM.writeId(packet);
		
		return true;
	}
}
