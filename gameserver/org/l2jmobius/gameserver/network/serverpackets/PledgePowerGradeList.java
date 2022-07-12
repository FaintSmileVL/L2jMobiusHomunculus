package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan.RankPrivs;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgePowerGradeList implements IClientOutgoingPacket
{
	private final RankPrivs[] _privs;
	
	public PledgePowerGradeList(RankPrivs[] privs)
	{
		_privs = privs;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_POWER_GRADE_LIST.writeId(packet);
		
		packet.writeD(_privs.length);
		for (RankPrivs temp : _privs)
		{
			packet.writeD(temp.getRank());
			packet.writeD(temp.getParty());
		}
		return true;
	}
}
