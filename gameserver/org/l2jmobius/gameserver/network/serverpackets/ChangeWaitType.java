package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ChangeWaitType implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _moveType;
	private final int _x;
	private final int _y;
	private final int _z;
	
	public static final int WT_SITTING = 0;
	public static final int WT_STANDING = 1;
	public static final int WT_START_FAKEDEATH = 2;
	public static final int WT_STOP_FAKEDEATH = 3;
	
	public ChangeWaitType(Creature creature, int newMoveType)
	{
		_objectId = creature.getObjectId();
		_moveType = newMoveType;
		_x = creature.getX();
		_y = creature.getY();
		_z = creature.getZ();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHANGE_WAIT_TYPE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_moveType);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
