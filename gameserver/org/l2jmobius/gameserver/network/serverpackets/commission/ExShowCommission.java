package org.l2jmobius.gameserver.network.serverpackets.commission;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author NosBit
 */
public class ExShowCommission implements IClientOutgoingPacket
{
	public static final ExShowCommission STATIC_PACKET = new ExShowCommission();
	
	private ExShowCommission()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_COMMISSION.writeId(packet);
		
		packet.writeD(0x01);
		return true;
	}
}
