package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class ExCubeGameTeamList implements IClientOutgoingPacket
{
	// Players Lists
	private final List<PlayerInstance> _bluePlayers;
	private final List<PlayerInstance> _redPlayers;
	
	// Common Values
	private final int _roomNumber;
	
	/**
	 * Show Minigame Waiting List to Player
	 * @param redPlayers Red Players List
	 * @param bluePlayers Blue Players List
	 * @param roomNumber Arena/Room ID
	 */
	public ExCubeGameTeamList(List<PlayerInstance> redPlayers, List<PlayerInstance> bluePlayers, int roomNumber)
	{
		_redPlayers = redPlayers;
		_bluePlayers = bluePlayers;
		_roomNumber = roomNumber - 1;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_LIST.writeId(packet);
		
		packet.writeD(0x00);
		
		packet.writeD(_roomNumber);
		packet.writeD(0xffffffff);
		
		packet.writeD(_bluePlayers.size());
		for (PlayerInstance player : _bluePlayers)
		{
			packet.writeD(player.getObjectId());
			packet.writeS(player.getName());
		}
		packet.writeD(_redPlayers.size());
		for (PlayerInstance player : _redPlayers)
		{
			packet.writeD(player.getObjectId());
			packet.writeS(player.getName());
		}
		return true;
	}
}
