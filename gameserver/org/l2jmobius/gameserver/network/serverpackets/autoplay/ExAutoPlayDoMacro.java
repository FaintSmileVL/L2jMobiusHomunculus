package org.l2jmobius.gameserver.network.serverpackets.autoplay;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExAutoPlayDoMacro implements IClientOutgoingPacket
{
	public static final ExAutoPlayDoMacro STATIC_PACKET = new ExAutoPlayDoMacro();
	
	public ExAutoPlayDoMacro()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_AUTOPLAY_DO_MACRO.writeId(packet);
		packet.writeD(0x114);
		return true;
	}
}
