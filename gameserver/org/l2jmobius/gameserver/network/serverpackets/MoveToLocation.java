package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class MoveToLocation implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _xDst;
	private final int _yDst;
	private final int _zDst;
	
	public MoveToLocation(Creature creature)
	{
		_objectId = creature.getObjectId();
		_x = creature.getX();
		_y = creature.getY();
		_z = creature.getZ();
		_xDst = creature.getXdestination();
		_yDst = creature.getYdestination();
		_zDst = creature.getZdestination();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MOVE_TO_LOCATION.writeId(packet);
		
		packet.writeD(_objectId);
		
		packet.writeD(_xDst);
		packet.writeD(_yDst);
		packet.writeD(_zDst);
		
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
