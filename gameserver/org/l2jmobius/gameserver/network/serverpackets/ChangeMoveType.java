package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ChangeMoveType implements IClientOutgoingPacket
{
	public static final int WALK = 0;
	public static final int RUN = 1;
	
	private final int _objectId;
	private final boolean _running;
	
	public ChangeMoveType(Creature creature)
	{
		_objectId = creature.getObjectId();
		_running = creature.isRunning();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHANGE_MOVE_TYPE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_running ? RUN : WALK);
		packet.writeD(0); // c2
		return true;
	}
}
