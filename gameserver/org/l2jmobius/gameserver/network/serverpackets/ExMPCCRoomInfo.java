package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.matching.CommandChannelMatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExMPCCRoomInfo implements IClientOutgoingPacket
{
	private final CommandChannelMatchingRoom _room;
	
	public ExMPCCRoomInfo(CommandChannelMatchingRoom room)
	{
		_room = room;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MPCC_ROOM_INFO.writeId(packet);
		
		packet.writeD(_room.getId());
		packet.writeD(_room.getMaxMembers());
		packet.writeD(_room.getMinLevel());
		packet.writeD(_room.getMaxLevel());
		packet.writeD(_room.getLootType());
		packet.writeD(_room.getLocation());
		packet.writeS(_room.getTitle());
		return true;
	}
}
