package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Kerberos
 */
public class VehicleStarted implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _state;
	
	/**
	 * @param boat
	 * @param state
	 */
	public VehicleStarted(Creature boat, int state)
	{
		_objectId = boat.getObjectId();
		_state = state;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VEHICLE_START.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_state);
		return true;
	}
}
