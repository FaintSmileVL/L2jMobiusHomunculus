package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class TeleportToLocation implements IClientOutgoingPacket
{
	private final int _targetObjId;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _heading;
	
	public TeleportToLocation(WorldObject obj, int x, int y, int z, int heading)
	{
		_targetObjId = obj.getObjectId();
		_x = x;
		_y = y;
		_z = z;
		_heading = heading;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.TELEPORT_TO_LOCATION.writeId(packet);
		
		packet.writeD(_targetObjId);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		packet.writeD(0x00); // Fade 0, Instant 1.
		packet.writeD(_heading);
		packet.writeD(0x00); // Unknown.
		return true;
	}
}
