package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class MoveToPawn implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _targetId;
	private final int _distance;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _tx;
	private final int _ty;
	private final int _tz;
	
	public MoveToPawn(Creature creature, WorldObject target, int distance)
	{
		_objectId = creature.getObjectId();
		_targetId = target.getObjectId();
		_distance = distance;
		_x = creature.getX();
		_y = creature.getY();
		_z = creature.getZ();
		_tx = target.getX();
		_ty = target.getY();
		_tz = target.getZ();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MOVE_TO_PAWN.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_targetId);
		packet.writeD(_distance);
		
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		packet.writeD(_tx);
		packet.writeD(_ty);
		packet.writeD(_tz);
		return true;
	}
}
