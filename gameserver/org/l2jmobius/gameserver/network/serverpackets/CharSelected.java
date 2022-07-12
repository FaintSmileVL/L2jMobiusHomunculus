package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.GameTimeController;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class CharSelected implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _sessionId;
	
	/**
	 * @param player
	 * @param sessionId
	 */
	public CharSelected(PlayerInstance player, int sessionId)
	{
		_player = player;
		_sessionId = sessionId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHARACTER_SELECTED.writeId(packet);
		
		packet.writeS(_player.getName());
		packet.writeD(_player.getObjectId());
		packet.writeS(_player.getTitle());
		packet.writeD(_sessionId);
		packet.writeD(_player.getClanId());
		packet.writeD(0x00); // ??
		packet.writeD(_player.getAppearance().isFemale() ? 1 : 0);
		packet.writeD(_player.getRace().ordinal());
		packet.writeD(_player.getClassId().getId());
		packet.writeD(0x01); // active ??
		packet.writeD(_player.getX());
		packet.writeD(_player.getY());
		packet.writeD(_player.getZ());
		packet.writeF(_player.getCurrentHp());
		packet.writeF(_player.getCurrentMp());
		packet.writeQ(_player.getSp());
		packet.writeQ(_player.getExp());
		packet.writeD(_player.getLevel());
		packet.writeD(_player.getReputation());
		packet.writeD(_player.getPkKills());
		packet.writeD(GameTimeController.getInstance().getGameTime() % (24 * 60)); // "reset" on 24th hour
		packet.writeD(0x00);
		packet.writeD(_player.getClassId().getId());
		
		packet.writeB(new byte[16]);
		
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		
		packet.writeD(0x00);
		
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		
		packet.writeB(new byte[28]);
		packet.writeD(0x00);
		return true;
	}
}
