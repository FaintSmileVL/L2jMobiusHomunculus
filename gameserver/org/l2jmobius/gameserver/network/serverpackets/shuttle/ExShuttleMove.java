package org.l2jmobius.gameserver.network.serverpackets.shuttle;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.ShuttleInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExShuttleMove implements IClientOutgoingPacket
{
	private final ShuttleInstance _shuttle;
	private final int _x;
	private final int _y;
	private final int _z;
	
	public ExShuttleMove(ShuttleInstance shuttle, int x, int y, int z)
	{
		_shuttle = shuttle;
		_x = x;
		_y = y;
		_z = z;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SUTTLE_MOVE.writeId(packet);
		
		packet.writeD(_shuttle.getObjectId());
		packet.writeD((int) _shuttle.getStat().getMoveSpeed());
		packet.writeD((int) _shuttle.getStat().getRotationSpeed());
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
