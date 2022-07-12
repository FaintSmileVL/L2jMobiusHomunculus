package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.entry.PledgeApplicantInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeWaitingUser implements IClientOutgoingPacket
{
	private final PledgeApplicantInfo _pledgeRecruitInfo;
	
	public ExPledgeWaitingUser(PledgeApplicantInfo pledgeRecruitInfo)
	{
		_pledgeRecruitInfo = pledgeRecruitInfo;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_WAITING_USER.writeId(packet);
		
		packet.writeD(_pledgeRecruitInfo.getPlayerId());
		packet.writeS(_pledgeRecruitInfo.getMessage());
		return true;
	}
}