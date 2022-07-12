package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class PledgeReceivePowerInfo implements IClientOutgoingPacket
{
	private final ClanMember _member;
	
	public PledgeReceivePowerInfo(ClanMember member)
	{
		_member = member;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_RECEIVE_POWER_INFO.writeId(packet);
		
		packet.writeD(_member.getPowerGrade()); // power grade
		packet.writeS(_member.getName());
		packet.writeD(_member.getClan().getRankPrivs(_member.getPowerGrade()).getBitmask()); // privileges
		return true;
	}
}
