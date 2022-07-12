package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.ClanEntryStatus;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeRecruitApplyInfo implements IClientOutgoingPacket
{
	private final ClanEntryStatus _status;
	
	public ExPledgeRecruitApplyInfo(ClanEntryStatus status)
	{
		_status = status;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_RECRUIT_APPLY_INFO.writeId(packet);
		
		packet.writeD(_status.ordinal());
		return true;
	}
}
