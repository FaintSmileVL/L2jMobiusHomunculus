package org.l2jmobius.gameserver.network.serverpackets.shuttle;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.ShuttleInstance;
import org.l2jmobius.gameserver.model.shuttle.ShuttleStop;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExShuttleInfo implements IClientOutgoingPacket
{
	private final ShuttleInstance _shuttle;
	private final List<ShuttleStop> _stops;
	
	public ExShuttleInfo(ShuttleInstance shuttle)
	{
		_shuttle = shuttle;
		_stops = shuttle.getStops();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHUTTLE_INFO.writeId(packet);
		
		packet.writeD(_shuttle.getObjectId());
		packet.writeD(_shuttle.getX());
		packet.writeD(_shuttle.getY());
		packet.writeD(_shuttle.getZ());
		packet.writeD(_shuttle.getHeading());
		packet.writeD(_shuttle.getId());
		packet.writeD(_stops.size());
		for (ShuttleStop stop : _stops)
		{
			packet.writeD(stop.getId());
			for (Location loc : stop.getDimensions())
			{
				packet.writeD(loc.getX());
				packet.writeD(loc.getY());
				packet.writeD(loc.getZ());
			}
			packet.writeD(stop.isDoorOpen() ? 0x01 : 0x00);
			packet.writeD(stop.hasDoorChanged() ? 0x01 : 0x00);
		}
		return true;
	}
}
