package org.l2jmobius.gameserver.network.serverpackets.shuttle;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.instance.ShuttleInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExShuttleGetOff implements IClientOutgoingPacket
{
	private final int _playerObjectId;
	private final int _shuttleObjectId;
	private final int _x;
	private final int _y;
	private final int _z;
	
	public ExShuttleGetOff(PlayerInstance player, ShuttleInstance shuttle, int x, int y, int z)
	{
		_playerObjectId = player.getObjectId();
		_shuttleObjectId = shuttle.getObjectId();
		_x = x;
		_y = y;
		_z = z;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SUTTLE_GET_OFF.writeId(packet);
		
		packet.writeD(_playerObjectId);
		packet.writeD(_shuttleObjectId);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		return true;
	}
}
