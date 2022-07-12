package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExWorldChatCnt implements IClientOutgoingPacket
{
	private final int _points;
	
	public ExWorldChatCnt(PlayerInstance player)
	{
		_points = player.getLevel() < Config.WORLD_CHAT_MIN_LEVEL ? 0 : Math.max(player.getWorldChatPoints() - player.getWorldChatUsed(), 0);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_WORLD_CHAT_CNT.writeId(packet);
		
		packet.writeD(_points);
		return true;
	}
}
