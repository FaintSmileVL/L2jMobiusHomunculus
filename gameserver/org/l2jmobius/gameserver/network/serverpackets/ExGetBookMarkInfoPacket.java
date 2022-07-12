package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.TeleportBookmark;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author ShanSoft
 */
public class ExGetBookMarkInfoPacket implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExGetBookMarkInfoPacket(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_GET_BOOK_MARK_INFO.writeId(packet);
		
		packet.writeD(0x00); // Dummy
		packet.writeD(_player.getBookMarkSlot());
		packet.writeD(_player.getTeleportBookmarks().size());
		
		for (TeleportBookmark tpbm : _player.getTeleportBookmarks())
		{
			packet.writeD(tpbm.getId());
			packet.writeD(tpbm.getX());
			packet.writeD(tpbm.getY());
			packet.writeD(tpbm.getZ());
			packet.writeS(tpbm.getName());
			packet.writeD(tpbm.getIcon());
			packet.writeS(tpbm.getTag());
		}
		return true;
	}
}
