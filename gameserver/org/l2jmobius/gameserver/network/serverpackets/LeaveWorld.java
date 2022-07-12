package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class LeaveWorld implements IClientOutgoingPacket
{
	public static final LeaveWorld STATIC_PACKET = new LeaveWorld();
	
	private LeaveWorld()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.LOG_OUT_OK.writeId(packet);
		return true;
	}
}
