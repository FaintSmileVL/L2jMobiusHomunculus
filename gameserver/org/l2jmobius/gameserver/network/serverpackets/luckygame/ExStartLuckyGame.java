package org.l2jmobius.gameserver.network.serverpackets.luckygame;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.LuckyGameType;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExStartLuckyGame implements IClientOutgoingPacket
{
	private final LuckyGameType _type;
	private final int _ticketCount;
	
	public ExStartLuckyGame(LuckyGameType type, long ticketCount)
	{
		_type = type;
		_ticketCount = (int) ticketCount;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_START_LUCKY_GAME.writeId(packet);
		packet.writeD(_type.ordinal());
		packet.writeD(_ticketCount);
		return true;
	}
}
