package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.MatchingMemberType;
import org.l2jmobius.gameserver.instancemanager.MapRegionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.CommandChannelMatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExMPCCRoomMember implements IClientOutgoingPacket
{
	private final CommandChannelMatchingRoom _room;
	private final MatchingMemberType _type;
	
	public ExMPCCRoomMember(PlayerInstance player, CommandChannelMatchingRoom room)
	{
		_room = room;
		_type = room.getMemberType(player);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MPCC_ROOM_MEMBER.writeId(packet);
		
		packet.writeD(_type.ordinal());
		packet.writeD(_room.getMembersCount());
		for (PlayerInstance member : _room.getMembers())
		{
			packet.writeD(member.getObjectId());
			packet.writeS(member.getName());
			packet.writeD(member.getLevel());
			packet.writeD(member.getClassId().getId());
			packet.writeD(MapRegionManager.getInstance().getBBs(member.getLocation()));
			packet.writeD(_room.getMemberType(member).ordinal());
		}
		return true;
	}
}
