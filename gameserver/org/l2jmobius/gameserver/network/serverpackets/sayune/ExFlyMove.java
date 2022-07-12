package org.l2jmobius.gameserver.network.serverpackets.sayune;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.SayuneType;
import org.l2jmobius.gameserver.model.SayuneEntry;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExFlyMove implements IClientOutgoingPacket
{
	private final int _objectId;
	private final SayuneType _type;
	private final int _mapId;
	private final List<SayuneEntry> _locations;
	
	public ExFlyMove(PlayerInstance player, SayuneType type, int mapId, List<SayuneEntry> locations)
	{
		_objectId = player.getObjectId();
		_type = type;
		_mapId = mapId;
		_locations = locations;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_FLY_MOVE.writeId(packet);
		
		packet.writeD(_objectId);
		
		packet.writeD(_type.ordinal());
		packet.writeD(0x00); // ??
		packet.writeD(_mapId);
		
		packet.writeD(_locations.size());
		for (SayuneEntry loc : _locations)
		{
			packet.writeD(loc.getId());
			packet.writeD(0x00); // ??
			packet.writeD(loc.getX());
			packet.writeD(loc.getY());
			packet.writeD(loc.getZ());
		}
		return true;
	}
}
