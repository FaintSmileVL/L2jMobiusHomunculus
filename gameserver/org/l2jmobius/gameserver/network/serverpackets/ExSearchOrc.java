package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class ExSearchOrc implements IClientOutgoingPacket
{
	public static final ExSearchOrc STATIC_PACKET = new ExSearchOrc();
	
	private ExSearchOrc()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SEARCH_ORC.writeId(packet);
		
		return true;
	}
}
