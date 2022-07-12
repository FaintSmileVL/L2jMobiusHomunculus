package org.l2jmobius.gameserver.network.serverpackets.shuttle;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExValidateLocationInShuttle implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _shipId;
	private final int _heading;
	private final Location _loc;
	
	public ExValidateLocationInShuttle(PlayerInstance player)
	{
		_player = player;
		_shipId = _player.getShuttle().getObjectId();
		_loc = player.getInVehiclePosition();
		_heading = player.getHeading();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_VALIDATE_LOCATION_IN_SHUTTLE.writeId(packet);
		
		packet.writeD(_player.getObjectId());
		packet.writeD(_shipId);
		packet.writeD(_loc.getX());
		packet.writeD(_loc.getY());
		packet.writeD(_loc.getZ());
		packet.writeD(_heading);
		return true;
	}
}
