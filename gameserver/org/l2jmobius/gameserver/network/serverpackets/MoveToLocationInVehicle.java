package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Maktakien
 */
public class MoveToLocationInVehicle implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _boatId;
	private final Location _destination;
	private final Location _origin;
	
	/**
	 * @param player
	 * @param destination
	 * @param origin
	 */
	public MoveToLocationInVehicle(PlayerInstance player, Location destination, Location origin)
	{
		_objectId = player.getObjectId();
		_boatId = player.getBoat().getObjectId();
		_destination = destination;
		_origin = origin;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MOVE_TO_LOCATION_IN_VEHICLE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_boatId);
		packet.writeD(_destination.getX());
		packet.writeD(_destination.getY());
		packet.writeD(_destination.getZ());
		packet.writeD(_origin.getX());
		packet.writeD(_origin.getY());
		packet.writeD(_origin.getZ());
		return true;
	}
}
