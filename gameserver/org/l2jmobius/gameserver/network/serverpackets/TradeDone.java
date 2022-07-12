package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class TradeDone implements IClientOutgoingPacket
{
	private final int _num;
	
	public TradeDone(int num)
	{
		_num = num;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.TRADE_DONE.writeId(packet);
		
		packet.writeD(_num);
		return true;
	}
}
