package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgeShowMemberListAll implements IClientOutgoingPacket
{
	private final Clan _clan;
	private final String _name;
	private final String _leaderName;
	private final Collection<ClanMember> _members;
	
	private PledgeShowMemberListAll(Clan clan, boolean isSubPledge)
	{
		_clan = clan;
		_leaderName = clan.getLeaderName();
		_name = clan.getName();
		_members = _clan.getMembers();
	}
	
	public static void sendAllTo(PlayerInstance player)
	{
		final Clan clan = player.getClan();
		player.sendPacket(new PledgeShowMemberListAll(clan, true));
		for (ClanMember member : clan.getMembers())
		{
			if (member.getPledgeType() != Clan.PLEDGE_CLASS_COMMON)
			{
				player.sendPacket(new PledgeShowMemberListUpdate(member));
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SHOW_MEMBER_LIST_ALL.writeId(packet);
		
		packet.writeD(0x00); // _isSubPledge
		packet.writeD(_clan.getId());
		packet.writeD(Config.SERVER_ID);
		packet.writeD(0x00);
		packet.writeS(_name);
		packet.writeS(_leaderName);
		
		packet.writeD(_clan.getCrestId()); // crest id .. is used again
		packet.writeD(_clan.getLevel());
		packet.writeD(_clan.getCastleId());
		packet.writeD(0x00);
		packet.writeD(_clan.getHideoutId());
		packet.writeD(_clan.getFortId());
		packet.writeD(_clan.getRank());
		packet.writeD(_clan.getReputationScore());
		packet.writeD(0x00); // 0
		packet.writeD(0x00); // 0
		packet.writeD(_clan.getAllyId());
		packet.writeS(_clan.getAllyName());
		packet.writeD(_clan.getAllyCrestId());
		packet.writeD(_clan.isAtWar() ? 1 : 0); // new c3
		packet.writeD(0x00); // Territory castle ID
		
		packet.writeD(_members.size());
		for (ClanMember m : _members)
		{
			packet.writeS(m.getName());
			packet.writeD(m.getLevel());
			packet.writeD(m.getClassId());
			packet.writeD(0); // sex
			packet.writeD(0); // race
			packet.writeD(m.isOnline() ? m.getObjectId() : 0); // objectId = online 0 = offline
			packet.writeD(0);
			packet.writeC(0);
		}
		return true;
	}
}
