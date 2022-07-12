package org.l2jmobius.gameserver.network.serverpackets.mentoring;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ListMenteeWaiting implements IClientOutgoingPacket
{
	private static final int PLAYERS_PER_PAGE = 64;
	private final List<PlayerInstance> _possibleCandiates = new ArrayList<>();
	private final int _page;
	
	public ListMenteeWaiting(int page, int minLevel, int maxLevel)
	{
		_page = page;
		for (PlayerInstance player : World.getInstance().getPlayers())
		{
			if ((player.getLevel() >= minLevel) && (player.getLevel() <= maxLevel) && !player.isMentee() && !player.isMentor() && !player.isInCategory(CategoryType.SIXTH_CLASS_GROUP))
			{
				_possibleCandiates.add(player);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.LIST_MENTEE_WAITING.writeId(packet);
		
		packet.writeD(0x01); // always 1 in retail
		if (_possibleCandiates.isEmpty())
		{
			packet.writeD(0x00);
			packet.writeD(0x00);
			return true;
		}
		
		packet.writeD(_possibleCandiates.size());
		packet.writeD(_possibleCandiates.size() % PLAYERS_PER_PAGE);
		
		for (PlayerInstance player : _possibleCandiates)
		{
			if ((1 <= (PLAYERS_PER_PAGE * _page)) && (1 > (PLAYERS_PER_PAGE * (_page - 1))))
			{
				packet.writeS(player.getName());
				packet.writeD(player.getActiveClass());
				packet.writeD(player.getLevel());
			}
		}
		return true;
	}
}
