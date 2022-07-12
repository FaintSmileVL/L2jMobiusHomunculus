package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SetSummonRemainTime implements IClientOutgoingPacket
{
	private final int _maxTime;
	private final int _remainingTime;
	
	public SetSummonRemainTime(int maxTime, int remainingTime)
	{
		_remainingTime = remainingTime;
		_maxTime = maxTime;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SET_SUMMON_REMAIN_TIME.writeId(packet);
		
		packet.writeD(_maxTime);
		packet.writeD(_remainingTime);
		return true;
	}
}
