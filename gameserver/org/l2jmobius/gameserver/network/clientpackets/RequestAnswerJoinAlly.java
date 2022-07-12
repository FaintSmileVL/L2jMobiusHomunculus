package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

public class RequestAnswerJoinAlly implements IClientIncomingPacket
{
	private int _response;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_response = packet.readD();
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
		
		final PlayerInstance requestor = player.getRequest().getPartner();
		if (requestor == null)
		{
			return;
		}
		
		if (_response == 0)
		{
			player.sendPacket(SystemMessageId.NO_RESPONSE_YOUR_ENTRANCE_TO_THE_ALLIANCE_HAS_BEEN_CANCELLED);
			requestor.sendPacket(SystemMessageId.NO_RESPONSE_INVITATION_TO_JOIN_AN_ALLIANCE_HAS_BEEN_CANCELLED);
		}
		else
		{
			if (!(requestor.getRequest().getRequestPacket() instanceof RequestJoinAlly))
			{
				return; // hax
			}
			
			final Clan clan = requestor.getClan();
			// we must double check this cause of hack
			if (clan.checkAllyJoinCondition(requestor, player))
			{
				// TODO: Need correct message id
				requestor.sendPacket(SystemMessageId.THAT_PERSON_HAS_BEEN_SUCCESSFULLY_ADDED_TO_YOUR_FRIEND_LIST);
				player.sendPacket(SystemMessageId.YOU_HAVE_ACCEPTED_THE_ALLIANCE);
				
				player.getClan().setAllyId(clan.getAllyId());
				player.getClan().setAllyName(clan.getAllyName());
				player.getClan().setAllyPenaltyExpiryTime(0, 0);
				player.getClan().changeAllyCrest(clan.getAllyCrestId(), true);
				player.getClan().updateClanInDB();
			}
		}
		
		player.getRequest().onRequestResponse();
	}
}
