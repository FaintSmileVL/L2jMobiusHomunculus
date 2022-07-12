package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

public class RequestReplySurrenderPledgeWar implements IClientIncomingPacket
{
	private String _reqName;
	private int _answer;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_reqName = packet.readS();
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
			ClanTable.getInstance().deleteClanWars(requestor.getClanId(), player.getClanId());
		}
		else
		{
			LOGGER.info(getClass().getSimpleName() + ": Missing implementation for answer: " + _answer + " and name: " + _reqName + "!");
		}
		player.onTransactionRequest(requestor);
	}
}