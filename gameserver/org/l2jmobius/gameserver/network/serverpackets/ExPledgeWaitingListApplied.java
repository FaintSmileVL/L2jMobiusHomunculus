package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.ClanEntryManager;
import org.l2jmobius.gameserver.model.clan.entry.PledgeApplicantInfo;
import org.l2jmobius.gameserver.model.clan.entry.PledgeRecruitInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeWaitingListApplied implements IClientOutgoingPacket
{
	private final PledgeApplicantInfo _pledgePlayerRecruitInfo;
	private final PledgeRecruitInfo _pledgeRecruitInfo;
	
	public ExPledgeWaitingListApplied(int clanId, int playerId)
	{
		_pledgePlayerRecruitInfo = ClanEntryManager.getInstance().getPlayerApplication(clanId, playerId);
		_pledgeRecruitInfo = ClanEntryManager.getInstance().getClanById(clanId);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_WAITING_LIST_APPLIED.writeId(packet);
		
		packet.writeD(_pledgeRecruitInfo.getClan().getId());
		packet.writeS(_pledgeRecruitInfo.getClan().getName());
		packet.writeS(_pledgeRecruitInfo.getClan().getLeaderName());
		packet.writeD(_pledgeRecruitInfo.getClan().getLevel());
		packet.writeD(_pledgeRecruitInfo.getClan().getMembersCount());
		packet.writeD(_pledgeRecruitInfo.getKarma());
		packet.writeS(_pledgeRecruitInfo.getInformation());
		packet.writeS(_pledgePlayerRecruitInfo.getMessage());
		return true;
	}
}
