package org.l2jmobius.gameserver.network.serverpackets.raidbossinfo;

import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExRaidBossSpawnInfo implements IClientOutgoingPacket
{
	private final Map<Integer, Integer> _statuses;
	
	public ExRaidBossSpawnInfo(Map<Integer, Integer> statuses)
	{
		_statuses = statuses;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RAID_BOSS_SPAWN_INFO.writeId(packet);
		
		packet.writeD(_statuses.size()); // count
		for (Entry<Integer, Integer> entry : _statuses.entrySet())
		{
			packet.writeD(entry.getKey());
			packet.writeD(entry.getValue());
			packet.writeD(0);
		}
		return true;
	}
}
