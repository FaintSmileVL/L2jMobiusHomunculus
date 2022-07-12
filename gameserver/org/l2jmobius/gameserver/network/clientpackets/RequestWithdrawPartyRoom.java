package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.MatchingRoomType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Gnacik
 */
public class RequestWithdrawPartyRoom implements IClientIncomingPacket
{
	private int _roomId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_roomId = packet.readD();
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
		if (room == null)
		{
			return;
		}
		
		if ((room.getId() != _roomId) || (room.getRoomType() != MatchingRoomType.PARTY))
		{
			return;
		}
		
		room.deleteMember(player, false);
	}
}
