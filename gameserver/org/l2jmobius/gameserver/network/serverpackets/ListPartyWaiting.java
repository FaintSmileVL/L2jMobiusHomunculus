package org.l2jmobius.gameserver.network.serverpackets;

import java.util.LinkedList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.PartyMatchingRoomLevelType;
import org.l2jmobius.gameserver.instancemanager.MatchingRoomManager;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class ListPartyWaiting implements IClientOutgoingPacket
{
	private final List<MatchingRoom> _rooms = new LinkedList<>();
	private final int _size;
	
	private static final int NUM_PER_PAGE = 64;
	
	public ListPartyWaiting(PartyMatchingRoomLevelType type, int location, int page, int requestorLevel)
	{
		final List<MatchingRoom> rooms = MatchingRoomManager.getInstance().getPartyMathchingRooms(location, type, requestorLevel);
		_size = rooms.size();
		final int startIndex = (page - 1) * NUM_PER_PAGE;
		int chunkSize = _size - startIndex;
		if (chunkSize > NUM_PER_PAGE)
		{
			chunkSize = NUM_PER_PAGE;
		}
		for (int i = startIndex; i < (startIndex + chunkSize); i++)
		{
			_rooms.add(rooms.get(i));
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.LIST_PARTY_WATING.writeId(packet);
		
		packet.writeD(_size);
		packet.writeD(_rooms.size());
		for (MatchingRoom room : _rooms)
		{
			packet.writeD(room.getId());
			packet.writeS(room.getTitle());
			packet.writeD(room.getLocation());
			packet.writeD(room.getMinLevel());
			packet.writeD(room.getMaxLevel());
			packet.writeD(room.getMaxMembers());
			packet.writeS(room.getLeader().getName());
			packet.writeD(room.getMembersCount());
			for (PlayerInstance member : room.getMembers())
			{
				packet.writeD(member.getClassId().getId());
				packet.writeS(member.getName());
			}
		}
		packet.writeD(World.getInstance().getPartyCount()); // Helios
		packet.writeD(World.getInstance().getPartyMemberCount()); // Helios
		return true;
	}
}
