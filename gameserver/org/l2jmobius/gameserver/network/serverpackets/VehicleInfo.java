package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.BoatInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Maktakien
 */
public class VehicleInfo implements IClientOutgoingPacket
{
	private final int _objId;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _heading;
	
	public VehicleInfo(BoatInstance boat)
	{
		_objId = boat.getObjectId();
		_x = boat.getX();
		_y = boat.getY();
		_z = boat.getZ();
		_heading = boat.getHeading();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VEHICLE_INFO.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		packet.writeD(_heading);
		return true;
	}
}
