package org.l2jmobius.gameserver.network.serverpackets.shuttle;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExMoveToLocationInShuttle implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _airShipId;
	private final int _targetX;
	private final int _targetY;
	private final int _targetZ;
	private final int _fromX;
	private final int _fromY;
	private final int _fromZ;
	
	public ExMoveToLocationInShuttle(PlayerInstance player, int fromX, int fromY, int fromZ)
	{
		_objectId = player.getObjectId();
		_airShipId = player.getShuttle().getObjectId();
		_targetX = player.getInVehiclePosition().getX();
		_targetY = player.getInVehiclePosition().getY();
		_targetZ = player.getInVehiclePosition().getZ();
		_fromX = fromX;
		_fromY = fromY;
		_fromZ = fromZ;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MOVE_TO_LOCATION_IN_SUTTLE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_airShipId);
		packet.writeD(_targetX);
		packet.writeD(_targetY);
		packet.writeD(_targetZ);
		packet.writeD(_fromX);
		packet.writeD(_fromY);
		packet.writeD(_fromZ);
		return true;
	}
}
