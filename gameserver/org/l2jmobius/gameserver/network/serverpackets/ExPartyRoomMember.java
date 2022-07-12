package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.enums.MatchingMemberType;
import org.l2jmobius.gameserver.instancemanager.InstanceManager;
import org.l2jmobius.gameserver.instancemanager.MapRegionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.PartyMatchingRoom;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class ExPartyRoomMember implements IClientOutgoingPacket
{
	private final PartyMatchingRoom _room;
	private final MatchingMemberType _type;
	
	public ExPartyRoomMember(PlayerInstance player, PartyMatchingRoom room)
	{
		_room = room;
		_type = room.getMemberType(player);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PARTY_ROOM_MEMBER.writeId(packet);
		
		packet.writeD(_type.ordinal());
		packet.writeD(_room.getMembersCount());
		for (PlayerInstance member : _room.getMembers())
		{
			packet.writeD(member.getObjectId());
			packet.writeS(member.getName());
			packet.writeD(member.getActiveClass());
			packet.writeD(member.getLevel());
			packet.writeD(MapRegionManager.getInstance().getBBs(member.getLocation()));
			packet.writeD(_room.getMemberType(member).ordinal());
			final Map<Integer, Long> instanceTimes = InstanceManager.getInstance().getAllInstanceTimes(member);
			packet.writeD(instanceTimes.size());
			for (Entry<Integer, Long> entry : instanceTimes.entrySet())
			{
				final long instanceTime = TimeUnit.MILLISECONDS.toSeconds(entry.getValue() - Chronos.currentTimeMillis());
				packet.writeD(entry.getKey());
				packet.writeD((int) instanceTime);
			}
		}
		return true;
	}
}