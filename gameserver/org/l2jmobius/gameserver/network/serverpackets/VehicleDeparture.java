package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.BoatInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Maktakien
 */
public class VehicleDeparture implements IClientOutgoingPacket
{
	private final int _objId;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _moveSpeed;
	private final int _rotationSpeed;
	
	public VehicleDeparture(BoatInstance boat)
	{
		_objId = boat.getObjectId();
		_x = boat.getXdestination();
		_y = boat.getYdestination();
		_z = boat.getZdestination();
		_moveSpeed = (int) boat.getMoveSpeed();
		_rotationSpeed = (int) boat.getStat().getRotationSpeed();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VEHICLE_DEPARTURE.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeD(_moveSpeed);
		packet.writeD(_rotationSpeed);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
