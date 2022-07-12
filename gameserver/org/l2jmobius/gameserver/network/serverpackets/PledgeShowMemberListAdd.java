package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgeShowMemberListAdd implements IClientOutgoingPacket
{
	private final String _name;
	private final int _level;
	private final int _classId;
	private final int _isOnline;
	private final int _pledgeType;
	
	public PledgeShowMemberListAdd(PlayerInstance player)
	{
		_name = player.getName();
		_level = player.getLevel();
		_classId = player.getClassId().getId();
		_isOnline = (player.isOnline() ? player.getObjectId() : 0);
		_pledgeType = player.getPledgeType();
	}
	
	public PledgeShowMemberListAdd(ClanMember cm)
	{
		_name = cm.getName();
		_level = cm.getLevel();
		_classId = cm.getClassId();
		_isOnline = (cm.isOnline() ? cm.getObjectId() : 0);
		_pledgeType = cm.getPledgeType();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SHOW_MEMBER_LIST_ADD.writeId(packet);
		
		packet.writeS(_name);
		packet.writeD(_level);
		packet.writeD(_classId);
		packet.writeD(0x00);
		packet.writeD(0x01);
		packet.writeD(_isOnline); // 1 = online 0 = offline
		packet.writeD(_pledgeType);
		return true;
	}
}
