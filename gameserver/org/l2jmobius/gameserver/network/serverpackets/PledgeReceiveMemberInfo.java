package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class PledgeReceiveMemberInfo implements IClientOutgoingPacket
{
	private final ClanMember _member;
	
	public PledgeReceiveMemberInfo(ClanMember member)
	{
		_member = member;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_RECEIVE_MEMBER_INFO.writeId(packet);
		
		packet.writeD(_member.getPledgeType());
		packet.writeS(_member.getName());
		packet.writeS(_member.getTitle()); // title
		packet.writeD(_member.getPowerGrade()); // power
		packet.writeS(_member.getClan().getName());
		packet.writeS(_member.getApprenticeOrSponsorName()); // name of this member's apprentice/sponsor
		return true;
	}
}
