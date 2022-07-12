package org.l2jmobius.gameserver.network.serverpackets.sayune;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.SayuneType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.interfaces.ILocational;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExFlyMoveBroadcast implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _mapId;
	private final ILocational _currentLoc;
	private final ILocational _targetLoc;
	private final SayuneType _type;
	
	public ExFlyMoveBroadcast(PlayerInstance player, SayuneType type, int mapId, ILocational targetLoc)
	{
		_objectId = player.getObjectId();
		_type = type;
		_mapId = mapId;
		_currentLoc = player;
		_targetLoc = targetLoc;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_FLY_MOVE_BROADCAST.writeId(packet);
		
		packet.writeD(_objectId);
		
		packet.writeD(_type.ordinal());
		packet.writeD(_mapId);
		
		packet.writeD(_targetLoc.getX());
		packet.writeD(_targetLoc.getY());
		packet.writeD(_targetLoc.getZ());
		packet.writeD(0x00); // ?
		packet.writeD(_currentLoc.getX());
		packet.writeD(_currentLoc.getY());
		packet.writeD(_currentLoc.getZ());
		return true;
	}
}
