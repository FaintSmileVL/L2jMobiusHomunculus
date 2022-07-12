package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.ExManagePartyRoomMemberType;
import org.l2jmobius.gameserver.enums.MatchingMemberType;
import org.l2jmobius.gameserver.instancemanager.MapRegionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.CommandChannelMatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class ExManageMpccRoomMember implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final MatchingMemberType _memberType;
	private final ExManagePartyRoomMemberType _type;
	
	public ExManageMpccRoomMember(PlayerInstance player, CommandChannelMatchingRoom room, ExManagePartyRoomMemberType mode)
	{
		_player = player;
		_memberType = room.getMemberType(player);
		_type = mode;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MANAGE_PARTY_ROOM_MEMBER.writeId(packet);
		
		packet.writeD(_type.ordinal());
		packet.writeD(_player.getObjectId());
		packet.writeS(_player.getName());
		packet.writeD(_player.getClassId().getId());
		packet.writeD(_player.getLevel());
		packet.writeD(MapRegionManager.getInstance().getBBs(_player.getLocation()));
		packet.writeD(_memberType.ordinal());
		return true;
	}
}
