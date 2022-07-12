package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExMoveToLocationInAirShip implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _airShipId;
	private final Location _destination;
	private final int _heading;
	
	/**
	 * @param player
	 */
	public ExMoveToLocationInAirShip(PlayerInstance player)
	{
		_objectId = player.getObjectId();
		_airShipId = player.getAirShip().getObjectId();
		_destination = player.getInVehiclePosition();
		_heading = player.getHeading();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MOVE_TO_LOCATION_IN_AIR_SHIP.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_airShipId);
		packet.writeD(_destination.getX());
		packet.writeD(_destination.getY());
		packet.writeD(_destination.getZ());
		packet.writeD(_heading);
		return true;
	}
}
