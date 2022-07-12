package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class TradeOtherDone implements IClientOutgoingPacket
{
	public static final TradeOtherDone STATIC_PACKET = new TradeOtherDone();
	
	private TradeOtherDone()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.TRADE_PRESS_OTHER_OK.writeId(packet);
		return true;
	}
}
