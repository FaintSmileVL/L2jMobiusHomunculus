package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.MatchingRoomType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Gnacik
 */
public class RequestDismissPartyRoom implements IClientIncomingPacket
{
	private int _roomid;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_roomid = packet.readD();
		packet.readD();
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
		if ((room == null) || (room.getId() != _roomid) || (room.getRoomType() != MatchingRoomType.PARTY) || (room.getLeader() != player))
		{
			return;
		}
		
		room.disbandRoom();
	}
}
