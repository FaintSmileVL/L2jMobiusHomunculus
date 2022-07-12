package org.l2jmobius.gameserver.network.clientpackets;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.MatchingRoomType;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExMPCCPartymasterList;

/**
 * @author Sdw
 */
public class RequestExMpccPartymasterList implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		final MatchingRoom room = player.getMatchingRoom();
		if ((room != null) && (room.getRoomType() == MatchingRoomType.COMMAND_CHANNEL))
		{
			final Set<String> leadersName = room.getMembers().stream().map(PlayerInstance::getParty).filter(Objects::nonNull).map(Party::getLeader).map(PlayerInstance::getName).collect(Collectors.toSet());
			player.sendPacket(new ExMPCCPartymasterList(leadersName));
		}
	}
}
