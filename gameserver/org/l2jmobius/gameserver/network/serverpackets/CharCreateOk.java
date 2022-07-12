package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class CharCreateOk implements IClientOutgoingPacket
{
	public static final CharCreateOk STATIC_PACKET = new CharCreateOk();
	
	private CharCreateOk()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHARACTER_CREATE_SUCCESS.writeId(packet);
		
		packet.writeD(0x01);
		return true;
	}
}
