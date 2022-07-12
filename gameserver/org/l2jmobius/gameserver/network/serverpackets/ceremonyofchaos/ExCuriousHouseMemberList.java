package org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.ceremonyofchaos.CeremonyOfChaosMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExCuriousHouseMemberList implements IClientOutgoingPacket
{
	private final int _id;
	private final int _maxPlayers;
	private final Collection<CeremonyOfChaosMember> _players;
	
	public ExCuriousHouseMemberList(int id, int maxPlayers, Collection<CeremonyOfChaosMember> players)
	{
		_id = id;
		_maxPlayers = maxPlayers;
		_players = players;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CURIOUS_HOUSE_MEMBER_LIST.writeId(packet);
		
		packet.writeD(_id);
		packet.writeD(_maxPlayers);
		packet.writeD(_players.size());
		for (CeremonyOfChaosMember cocPlayer : _players)
		{
			final PlayerInstance player = cocPlayer.getPlayer();
			packet.writeD(cocPlayer.getObjectId());
			packet.writeD(cocPlayer.getPosition());
			if (player != null)
			{
				packet.writeD(player.getMaxHp());
				packet.writeD(player.getMaxCp());
				packet.writeD((int) player.getCurrentHp());
				packet.writeD((int) player.getCurrentCp());
			}
			else
			{
				packet.writeD(0x00);
				packet.writeD(0x00);
				packet.writeD(0x00);
				packet.writeD(0x00);
			}
		}
		return true;
	}
}
