package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.interfaces.ILocational;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Note: <b>There is known issue with this packet, it cannot be removed unless game client is restarted!</b>
 * @author UnAfraid
 */
public class ExShowTerritory implements IClientOutgoingPacket
{
	private final int _minZ;
	private final int _maxZ;
	private final List<ILocational> _vertices = new ArrayList<>();
	
	public ExShowTerritory(int minZ, int maxZ)
	{
		_minZ = minZ;
		_maxZ = maxZ;
	}
	
	public void addVertice(ILocational loc)
	{
		_vertices.add(loc);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_TERRITORY.writeId(packet);
		
		packet.writeD(_vertices.size());
		packet.writeD(_minZ);
		packet.writeD(_maxZ);
		for (ILocational loc : _vertices)
		{
			packet.writeD(loc.getX());
			packet.writeD(loc.getY());
		}
		return true;
	}
}
