package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * update 27.8.10
 * @author kerberos, JIV
 */
public class ExStopMoveInAirShip implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _shipObjId;
	private final int _h;
	private final Location _loc;
	
	public ExStopMoveInAirShip(PlayerInstance player, int shipObjId)
	{
		_player = player;
		_shipObjId = shipObjId;
		_h = player.getHeading();
		_loc = player.getInVehiclePosition();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_STOP_MOVE_IN_AIR_SHIP.writeId(packet);
		
		packet.writeD(_player.getObjectId());
		packet.writeD(_shipObjId);
		packet.writeD(_loc.getX());
		packet.writeD(_loc.getY());
		packet.writeD(_loc.getZ());
		packet.writeD(_h);
		return true;
	}
}
