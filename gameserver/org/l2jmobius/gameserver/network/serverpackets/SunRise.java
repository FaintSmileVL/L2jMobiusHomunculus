package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SunRise implements IClientOutgoingPacket
{
	public static final SunRise STATIC_PACKET = new SunRise();
	
	private SunRise()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SUN_RISE.writeId(packet);
		return true;
	}
}
