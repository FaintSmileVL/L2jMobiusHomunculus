package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class ExCubeGameExtendedChangePoints implements IClientOutgoingPacket
{
	int _timeLeft;
	int _bluePoints;
	int _redPoints;
	boolean _isRedTeam;
	PlayerInstance _player;
	int _playerPoints;
	
	/**
	 * Update a Secret Point Counter (used by client when receive ExCubeGameEnd)
	 * @param timeLeft Time Left before Minigame's End
	 * @param bluePoints Current Blue Team Points
	 * @param redPoints Current Blue Team points
	 * @param isRedTeam Is Player from Red Team?
	 * @param player Player Instance
	 * @param playerPoints Current Player Points
	 */
	public ExCubeGameExtendedChangePoints(int timeLeft, int bluePoints, int redPoints, boolean isRedTeam, PlayerInstance player, int playerPoints)
	{
		_timeLeft = timeLeft;
		_bluePoints = bluePoints;
		_redPoints = redPoints;
		_isRedTeam = isRedTeam;
		_player = player;
		_playerPoints = playerPoints;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_STATE.writeId(packet);
		
		packet.writeD(0x00);
		
		packet.writeD(_timeLeft);
		packet.writeD(_bluePoints);
		packet.writeD(_redPoints);
		
		packet.writeD(_isRedTeam ? 0x01 : 0x00);
		packet.writeD(_player.getObjectId());
		packet.writeD(_playerPoints);
		return true;
	}
}
