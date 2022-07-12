package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class GMViewPledgeInfo implements IClientOutgoingPacket
{
	private final Clan _clan;
	private final PlayerInstance _player;
	
	public GMViewPledgeInfo(Clan clan, PlayerInstance player)
	{
		_clan = clan;
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GM_VIEW_PLEDGE_INFO.writeId(packet);
		
		packet.writeD(0x00);
		packet.writeS(_player.getName());
		packet.writeD(_clan.getId());
		packet.writeD(0x00);
		packet.writeS(_clan.getName());
		packet.writeS(_clan.getLeaderName());
		
		packet.writeD(_clan.getCrestId()); // -> no, it's no longer used (nuocnam) fix by game
		packet.writeD(_clan.getLevel());
		packet.writeD(_clan.getCastleId());
		packet.writeD(_clan.getHideoutId());
		packet.writeD(_clan.getFortId());
		packet.writeD(_clan.getRank());
		packet.writeD(_clan.getReputationScore());
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(_clan.getAllyId()); // c2
		packet.writeS(_clan.getAllyName()); // c2
		packet.writeD(_clan.getAllyCrestId()); // c2
		packet.writeD(_clan.isAtWar() ? 1 : 0); // c3
		packet.writeD(0x00); // T3 Unknown
		
		packet.writeD(_clan.getMembers().size());
		for (ClanMember member : _clan.getMembers())
		{
			if (member != null)
			{
				packet.writeS(member.getName());
				packet.writeD(member.getLevel());
				packet.writeD(member.getClassId());
				packet.writeD(member.getSex() ? 1 : 0);
				packet.writeD(member.getRaceOrdinal());
				packet.writeD(member.isOnline() ? member.getObjectId() : 0);
				packet.writeD(member.getSponsor() != 0 ? 1 : 0);
			}
		}
		return true;
	}
}
