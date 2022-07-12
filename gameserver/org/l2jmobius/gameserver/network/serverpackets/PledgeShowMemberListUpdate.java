package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class PledgeShowMemberListUpdate implements IClientOutgoingPacket
{
	private final int _pledgeType;
	private final String _name;
	private final int _level;
	private final int _classId;
	private final int _objectId;
	
	public PledgeShowMemberListUpdate(PlayerInstance player)
	{
		this(player.getClan().getClanMember(player.getObjectId()));
	}
	
	public PledgeShowMemberListUpdate(ClanMember member)
	{
		_name = member.getName();
		_level = member.getLevel();
		_classId = member.getClassId();
		_objectId = member.isOnline() ? member.getObjectId() : 0;
		_pledgeType = member.getPledgeType();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SHOW_MEMBER_LIST_UPDATE.writeId(packet);
		
		packet.writeS(_name);
		packet.writeD(_level);
		packet.writeD(_classId);
		packet.writeD(0); // _sex
		packet.writeD(0); // _race
		packet.writeD(_objectId);
		packet.writeD(_pledgeType);
		packet.writeD(0); // _hasSponsor
		packet.writeC(0);
		return true;
	}
}
