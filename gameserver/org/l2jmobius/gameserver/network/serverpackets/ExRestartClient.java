package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class ExRestartClient implements IClientOutgoingPacket
{
	public static final ExRestartClient STATIC_PACKET = new ExRestartClient();
	
	private ExRestartClient()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESTART_CLIENT.writeId(packet);
		
		return true;
	}
}
