package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.enums.ClanWarState;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanWar;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * @version $Revision: 1.4.2.1.2.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestReplyStartPledgeWar implements IClientIncomingPacket
{
	private int _answer;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readS();
		_answer = packet.readD();
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
		final PlayerInstance requestor = player.getActiveRequester();
		if (requestor == null)
		{
			return;
		}
		
		if (_answer == 1)
		{
			final Clan attacked = player.getClan();
			final Clan attacker = requestor.getClan();
			if ((attacked != null) && (attacker != null))
			{
				final ClanWar clanWar = attacker.getWarWith(attacked.getId());
				if (clanWar.getState() == ClanWarState.BLOOD_DECLARATION)
				{
					clanWar.mutualClanWarAccepted(attacker, attacked);
					ClanTable.getInstance().storeClanWars(clanWar);
				}
			}
		}
		else
		{
			requestor.sendPacket(SystemMessageId.THE_S1_CLAN_DID_NOT_RESPOND_WAR_PROCLAMATION_HAS_BEEN_REFUSED_2);
		}
		player.setActiveRequester(null);
		requestor.onTransactionResponse();
	}
}
