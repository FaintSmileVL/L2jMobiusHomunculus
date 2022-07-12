package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ValidateLocation implements IClientOutgoingPacket
{
	private final int _objectId;
	private final Location _loc;
	
	public ValidateLocation(WorldObject obj)
	{
		_objectId = obj.getObjectId();
		_loc = obj.getLocation();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VALIDATE_LOCATION.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_loc.getX());
		packet.writeD(_loc.getY());
		packet.writeD(_loc.getZ());
		packet.writeD(_loc.getHeading());
		packet.writeC(0xFF); // TODO: Find me!
		return true;
	}
}
