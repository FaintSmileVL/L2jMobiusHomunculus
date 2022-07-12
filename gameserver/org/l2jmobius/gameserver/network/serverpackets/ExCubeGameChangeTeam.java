package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class ExCubeGameChangeTeam implements IClientOutgoingPacket
{
	PlayerInstance _player;
	boolean _fromRedTeam;
	
	/**
	 * Move Player from Team x to Team y
	 * @param player Player Instance
	 * @param fromRedTeam Is Player from Red Team?
	 */
	public ExCubeGameChangeTeam(PlayerInstance player, boolean fromRedTeam)
	{
		_player = player;
		_fromRedTeam = fromRedTeam;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_LIST.writeId(packet);
		
		packet.writeD(0x05);
		
		packet.writeD(_player.getObjectId());
		packet.writeD(_fromRedTeam ? 0x01 : 0x00);
		packet.writeD(_fromRedTeam ? 0x00 : 0x01);
		return true;
	}
}
