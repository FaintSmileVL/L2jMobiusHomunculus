package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.entry.PledgeWaitingInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeDraftListSearch implements IClientOutgoingPacket
{
	final List<PledgeWaitingInfo> _pledgeRecruitList;
	
	public ExPledgeDraftListSearch(List<PledgeWaitingInfo> pledgeRecruitList)
	{
		_pledgeRecruitList = pledgeRecruitList;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_DRAFT_LIST_SEARCH.writeId(packet);
		
		packet.writeD(_pledgeRecruitList.size());
		for (PledgeWaitingInfo prl : _pledgeRecruitList)
		{
			packet.writeD(prl.getPlayerId());
			packet.writeS(prl.getPlayerName());
			packet.writeD(prl.getKarma());
			packet.writeD(prl.getPlayerClassId());
			packet.writeD(prl.getPlayerLvl());
		}
		return true;
	}
}
