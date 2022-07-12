package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Maktakien
 */
public class GetOnVehicle implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _boatObjId;
	private final Location _pos;
	
	/**
	 * @param charObjId
	 * @param boatObjId
	 * @param pos
	 */
	public GetOnVehicle(int charObjId, int boatObjId, Location pos)
	{
		_objectId = charObjId;
		_boatObjId = boatObjId;
		_pos = pos;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GET_ON_VEHICLE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_boatObjId);
		packet.writeD(_pos.getX());
		packet.writeD(_pos.getY());
		packet.writeD(_pos.getZ());
		return true;
	}
}
