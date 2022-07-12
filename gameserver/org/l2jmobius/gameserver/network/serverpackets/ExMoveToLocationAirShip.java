package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExMoveToLocationAirShip implements IClientOutgoingPacket
{
	private final int _objId;
	private final int _tx;
	private final int _ty;
	private final int _tz;
	private final int _x;
	private final int _y;
	private final int _z;
	
	public ExMoveToLocationAirShip(Creature creature)
	{
		_objId = creature.getObjectId();
		_tx = creature.getXdestination();
		_ty = creature.getYdestination();
		_tz = creature.getZdestination();
		_x = creature.getX();
		_y = creature.getY();
		_z = creature.getZ();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MOVE_TO_LOCATION_AIR_SHIP.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeD(_tx);
		packet.writeD(_ty);
		packet.writeD(_tz);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
