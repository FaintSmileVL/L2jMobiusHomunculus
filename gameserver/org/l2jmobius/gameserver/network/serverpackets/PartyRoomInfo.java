package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.matching.PartyMatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class PartyRoomInfo implements IClientOutgoingPacket
{
	private final PartyMatchingRoom _room;
	
	public PartyRoomInfo(PartyMatchingRoom room)
	{
		_room = room;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PARTY_ROOM_INFO.writeId(packet);
		
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
