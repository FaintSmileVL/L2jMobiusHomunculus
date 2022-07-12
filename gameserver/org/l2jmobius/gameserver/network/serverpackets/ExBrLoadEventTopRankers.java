package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Halloween rank list server packet.
 */
public class ExBrLoadEventTopRankers implements IClientOutgoingPacket
{
	private final int _eventId;
	private final int _day;
	private final int _count;
	private final int _bestScore;
	private final int _myScore;
	
	public ExBrLoadEventTopRankers(int eventId, int day, int count, int bestScore, int myScore)
	{
		_eventId = eventId;
		_day = day;
		_count = count;
		_bestScore = bestScore;
		_myScore = myScore;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BR_LOAD_EVENT_TOP_RANKERS.writeId(packet);
		
		packet.writeD(_eventId);
		packet.writeD(_day);
		packet.writeD(_count);
		packet.writeD(_bestScore);
		packet.writeD(_myScore);
		return true;
	}
}
