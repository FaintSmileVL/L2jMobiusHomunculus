package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class Ride implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _mounted;
	private final int _rideType;
	private final int _rideNpcId;
	private final Location _loc;
	
	public Ride(PlayerInstance player)
	{
		_objectId = player.getObjectId();
		_mounted = player.isMounted() ? 1 : 0;
		_rideType = player.getMountType().ordinal();
		_rideNpcId = player.getMountNpcId() + 1000000;
		_loc = player.getLocation();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.RIDE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_mounted);
		packet.writeD(_rideType);
		packet.writeD(_rideNpcId);
		packet.writeD(_loc.getX());
		packet.writeD(_loc.getY());
		packet.writeD(_loc.getZ());
		return true;
	}
}
