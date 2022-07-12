package org.l2jmobius.gameserver.network.serverpackets.commission;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author NosBit
 */
public class ExCloseCommission implements IClientOutgoingPacket
{
	public static final ExCloseCommission STATIC_PACKET = new ExCloseCommission();
	
	private ExCloseCommission()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CLOSE_COMMISSION.writeId(packet);
		return true;
	}
}
