package org.l2jmobius.gameserver.network.serverpackets;

import java.util.LinkedList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.MatchingRoomManager;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExListMpccWaiting implements IClientOutgoingPacket
{
	private static final int NUM_PER_PAGE = 64;
	private final int _size;
	private final List<MatchingRoom> _rooms = new LinkedList<>();
	
	public ExListMpccWaiting(int page, int location, int level)
	{
		final List<MatchingRoom> rooms = MatchingRoomManager.getInstance().getCCMathchingRooms(location, level);
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
		OutgoingPackets.EX_LIST_MPCC_WAITING.writeId(packet);
		
		packet.writeD(_size);
		packet.writeD(_rooms.size());
		for (MatchingRoom room : _rooms)
		{
			packet.writeD(room.getId());
			packet.writeS(room.getTitle());
			packet.writeD(room.getMembersCount());
			packet.writeD(room.getMinLevel());
			packet.writeD(room.getMaxLevel());
			packet.writeD(room.getLocation());
			packet.writeD(room.getMaxMembers());
			packet.writeS(room.getLeader().getName());
		}
		return true;
	}
}
