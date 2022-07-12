package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class JoinPledge implements IClientOutgoingPacket
{
	private final int _pledgeId;
	
	public JoinPledge(int pledgeId)
	{
		_pledgeId = pledgeId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.JOIN_PLEDGE.writeId(packet);
		
		packet.writeD(_pledgeId);
		return true;
	}
}
