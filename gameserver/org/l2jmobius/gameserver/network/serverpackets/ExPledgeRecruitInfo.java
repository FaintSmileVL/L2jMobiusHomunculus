package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.Clan.SubPledge;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeRecruitInfo implements IClientOutgoingPacket
{
	private final Clan _clan;
	
	public ExPledgeRecruitInfo(int clanId)
	{
		_clan = ClanTable.getInstance().getClan(clanId);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_RECRUIT_INFO.writeId(packet);
		
		final SubPledge[] subPledges = _clan.getAllSubPledges();
		packet.writeS(_clan.getName());
		packet.writeS(_clan.getLeaderName());
		packet.writeD(_clan.getLevel());
		packet.writeD(_clan.getMembersCount());
		packet.writeD(subPledges.length);
		for (SubPledge subPledge : subPledges)
		{
			packet.writeD(subPledge.getId());
			packet.writeS(subPledge.getName());
		}
		return true;
	}
}
