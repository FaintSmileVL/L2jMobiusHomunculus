package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.interfaces.ILocational;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * This packet shows the mouse click particle for 30 seconds on every location.
 * @author NosBit
 */
public class ExShowTrace implements IClientOutgoingPacket
{
	private final List<Location> _locations = new ArrayList<>();
	
	public void addLocation(int x, int y, int z)
	{
		_locations.add(new Location(x, y, z));
	}
	
	public void addLocation(ILocational loc)
	{
		addLocation(loc.getX(), loc.getY(), loc.getZ());
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_TRACE.writeId(packet);
		
		packet.writeH(0); // type broken in H5
		packet.writeD(0); // time broken in H5
		packet.writeH(_locations.size());
		for (Location loc : _locations)
		{
			packet.writeD(loc.getX());
			packet.writeD(loc.getY());
			packet.writeD(loc.getZ());
		}
		return true;
	}
}
