package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class ExPVPMatchCCMyRecord implements IClientOutgoingPacket
{
	private final int _points;
	
	public ExPVPMatchCCMyRecord(int points)
	{
		_points = points;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PVP_MATCH_CCMY_RECORD.writeId(packet);
		packet.writeD(_points);
		return true;
	}
}
