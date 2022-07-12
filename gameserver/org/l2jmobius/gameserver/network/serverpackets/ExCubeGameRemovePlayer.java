package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class ExCubeGameRemovePlayer implements IClientOutgoingPacket
{
	PlayerInstance _player;
	boolean _isRedTeam;
	
	/**
	 * Remove Player from Minigame Waiting List
	 * @param player Player to Remove
	 * @param isRedTeam Is Player from Red Team?
	 */
	public ExCubeGameRemovePlayer(PlayerInstance player, boolean isRedTeam)
	{
		_player = player;
		_isRedTeam = isRedTeam;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_LIST.writeId(packet);
		
		packet.writeD(0x02);
		
		packet.writeD(0xffffffff);
		
		packet.writeD(_isRedTeam ? 0x01 : 0x00);
		packet.writeD(_player.getObjectId());
		return true;
	}
}
