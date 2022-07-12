package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExRegenMax implements IClientOutgoingPacket
{
	private final int _time;
	private final int _tickInterval;
	private final double _amountPerTick;
	
	public ExRegenMax(int time, int tickInterval, double amountPerTick)
	{
		_time = time;
		_tickInterval = tickInterval;
		_amountPerTick = amountPerTick;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_REGEN_MAX.writeId(packet);
		
		packet.writeD(1);
		packet.writeD(_time);
		packet.writeD(_tickInterval);
		packet.writeF(_amountPerTick);
		return true;
	}
}
