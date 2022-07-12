package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Maktakien
 */
public class GetOffVehicle implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _boatObjId;
	private final int _x;
	private final int _y;
	private final int _z;
	
	/**
	 * @param charObjId
	 * @param boatObjId
	 * @param x
	 * @param y
	 * @param z
	 */
	public GetOffVehicle(int charObjId, int boatObjId, int x, int y, int z)
	{
		_objectId = charObjId;
		_boatObjId = boatObjId;
		_x = x;
		_y = y;
		_z = z;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GET_OFF_VEHICLE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_boatObjId);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
