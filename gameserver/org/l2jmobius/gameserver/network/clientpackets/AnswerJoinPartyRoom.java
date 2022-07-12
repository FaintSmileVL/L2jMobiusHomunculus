package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Format: (ch) d
 * @author -Wooden-, Tryskell
 */
public class AnswerJoinPartyRoom implements IClientIncomingPacket
{
	private boolean _answer;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_answer = packet.readD() == 1;
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
		
		final PlayerInstance partner = player.getActiveRequester();
		if (partner == null)
		{
			player.sendPacket(SystemMessageId.THAT_PLAYER_IS_NOT_ONLINE);
			player.setActiveRequester(null);
			return;
		}
		
		if (_answer && !partner.isRequestExpired())
		{
			final MatchingRoom room = partner.getMatchingRoom();
			if (room == null)
			{
				return;
			}
			
			room.addMember(player);
		}
		else
		{
			partner.sendPacket(SystemMessageId.THE_RECIPIENT_OF_YOUR_INVITATION_DID_NOT_ACCEPT_THE_PARTY_MATCHING_INVITATION);
		}
		
		// reset transaction timers
		player.setActiveRequester(null);
		partner.onTransactionResponse();
	}
}
