package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Maktakien
 */
public class VehicleCheckLocation implements IClientOutgoingPacket
{
	private final Creature _boat;
	
	public VehicleCheckLocation(Creature boat)
	{
		_boat = boat;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VEHICLE_CHECK_LOCATION.writeId(packet);
		
		packet.writeD(_boat.getObjectId());
		packet.writeD(_boat.getX());
		packet.writeD(_boat.getY());
		packet.writeD(_boat.getZ());
		packet.writeD(_boat.getHeading());
		return true;
	}
}
