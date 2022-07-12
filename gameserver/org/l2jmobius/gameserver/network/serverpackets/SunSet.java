package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SunSet implements IClientOutgoingPacket
{
	public static final SunSet STATIC_PACKET = new SunSet();
	
	private SunSet()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SUN_SET.writeId(packet);
		return true;
	}
}
