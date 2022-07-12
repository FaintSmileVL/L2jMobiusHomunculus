package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author zabbix
 */
public class PartyMemberPosition implements IClientOutgoingPacket
{
	private final Map<Integer, Location> locations = new HashMap<>();
	
	public PartyMemberPosition(Party party)
	{
		reuse(party);
	}
	
	public void reuse(Party party)
	{
		locations.clear();
		for (PlayerInstance member : party.getMembers())
		{
			if (member == null)
			{
				continue;
			}
			locations.put(member.getObjectId(), member.getLocation());
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PARTY_MEMBER_POSITION.writeId(packet);
		
		packet.writeD(locations.size());
		for (Map.Entry<Integer, Location> entry : locations.entrySet())
		{
			final Location loc = entry.getValue();
			packet.writeD(entry.getKey());
			packet.writeD(loc.getX());
			packet.writeD(loc.getY());
			packet.writeD(loc.getZ());
		}
		return true;
	}
}
