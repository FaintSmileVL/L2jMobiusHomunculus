package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ValidateLocationInVehicle implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _boatObjId;
	private final int _heading;
	private final Location _pos;
	
	public ValidateLocationInVehicle(PlayerInstance player)
	{
		_objectId = player.getObjectId();
		_boatObjId = player.getBoat().getObjectId();
		_heading = player.getHeading();
		_pos = player.getInVehiclePosition();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VALIDATE_LOCATION_IN_VEHICLE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_boatObjId);
		packet.writeD(_pos.getX());
		packet.writeD(_pos.getY());
		packet.writeD(_pos.getZ());
		packet.writeD(_heading);
		return true;
	}
}
