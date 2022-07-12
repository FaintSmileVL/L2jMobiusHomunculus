package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExDuelUpdateUserInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExDuelUpdateUserInfo(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DUEL_UPDATE_USER_INFO.writeId(packet);
		
		packet.writeS(_player.getName());
		packet.writeD(_player.getObjectId());
		packet.writeD(_player.getClassId().getId());
		packet.writeD(_player.getLevel());
		packet.writeD((int) _player.getCurrentHp());
		packet.writeD(_player.getMaxHp());
		packet.writeD((int) _player.getCurrentMp());
		packet.writeD(_player.getMaxMp());
		packet.writeD((int) _player.getCurrentCp());
		packet.writeD(_player.getMaxCp());
		return true;
	}
}
