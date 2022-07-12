package org.l2jmobius.gameserver.network.serverpackets.shuttle;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExStopMoveInShuttle implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _boatId;
	private final Location _pos;
	private final int _heading;
	
	public ExStopMoveInShuttle(PlayerInstance player, int boatId)
	{
		_objectId = player.getObjectId();
		_boatId = boatId;
		_pos = player.getInVehiclePosition();
		_heading = player.getHeading();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_STOP_MOVE_IN_SHUTTLE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_boatId);
		packet.writeD(_pos.getX());
		packet.writeD(_pos.getY());
		packet.writeD(_pos.getZ());
		packet.writeD(_heading);
		return true;
	}
}
