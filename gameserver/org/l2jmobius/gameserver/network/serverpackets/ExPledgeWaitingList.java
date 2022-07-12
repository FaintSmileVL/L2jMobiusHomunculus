package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.ClanEntryManager;
import org.l2jmobius.gameserver.model.clan.entry.PledgeApplicantInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeWaitingList implements IClientOutgoingPacket
{
	private final Map<Integer, PledgeApplicantInfo> pledgePlayerRecruitInfos;
	
	public ExPledgeWaitingList(int clanId)
	{
		pledgePlayerRecruitInfos = ClanEntryManager.getInstance().getApplicantListForClan(clanId);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_WAITING_LIST.writeId(packet);
		
		packet.writeD(pledgePlayerRecruitInfos.size());
		for (PledgeApplicantInfo recruitInfo : pledgePlayerRecruitInfos.values())
		{
			packet.writeD(recruitInfo.getPlayerId());
			packet.writeS(recruitInfo.getPlayerName());
			packet.writeD(recruitInfo.getClassId());
			packet.writeD(recruitInfo.getPlayerLvl());
		}
		return true;
	}
}
