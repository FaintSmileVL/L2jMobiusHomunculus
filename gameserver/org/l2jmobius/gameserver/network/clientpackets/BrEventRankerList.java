package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExBrLoadEventTopRankers;

/**
 * Halloween rank list client packet. Format: (ch)ddd
 */
public class BrEventRankerList implements IClientIncomingPacket
{
	private int _eventId;
	private int _day;
	@SuppressWarnings("unused")
	private int _ranking;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_eventId = packet.readD();
		_day = packet.readD(); // 0 - current, 1 - previous
		_ranking = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		// TODO count, bestScore, myScore
		final int count = 0;
		final int bestScore = 0;
		final int myScore = 0;
		client.sendPacket(new ExBrLoadEventTopRankers(_eventId, _day, count, bestScore, myScore));
	}
}
