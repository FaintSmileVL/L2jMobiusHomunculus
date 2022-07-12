package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class NormalCamera implements IClientOutgoingPacket
{
	public static final NormalCamera STATIC_PACKET = new NormalCamera();
	
	private NormalCamera()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.NORMAL_CAMERA.writeId(packet);
		return true;
	}
}
