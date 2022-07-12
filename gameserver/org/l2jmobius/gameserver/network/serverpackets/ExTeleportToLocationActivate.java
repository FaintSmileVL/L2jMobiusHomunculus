package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExTeleportToLocationActivate implements IClientOutgoingPacket
{
	private final int _objectId;
	private final Location _loc;
	
	public ExTeleportToLocationActivate(Creature creature)
	{
		_objectId = creature.getObjectId();
		_loc = creature.getLocation();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TELEPORT_TO_LOCATION_ACTIVATE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_loc.getX());
		packet.writeD(_loc.getY());
		packet.writeD(_loc.getZ());
		packet.writeD(0); // Unknown (this isn't instanceId)
		packet.writeD(_loc.getHeading());
		packet.writeD(0); // Unknown
		return true;
	}
}