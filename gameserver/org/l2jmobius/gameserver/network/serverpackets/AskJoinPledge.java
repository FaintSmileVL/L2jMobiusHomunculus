package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class AskJoinPledge implements IClientOutgoingPacket
{
	private final PlayerInstance _requestor;
	private final int _pledgeType;
	private final String _pledgeName;
	
	public AskJoinPledge(PlayerInstance requestor, int pledgeType, String pledgeName)
	{
		_requestor = requestor;
		_pledgeType = pledgeType;
		_pledgeName = pledgeName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ASK_JOIN_PLEDGE.writeId(packet);
		packet.writeD(_requestor.getObjectId());
		packet.writeS(_requestor.getName());
		packet.writeS(_pledgeName);
		if (_pledgeType != 0)
		{
			packet.writeD(_pledgeType);
		}
		return true;
	}
}
