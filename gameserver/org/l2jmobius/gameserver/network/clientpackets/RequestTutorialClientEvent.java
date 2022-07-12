package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.network.GameClient;

public class RequestTutorialClientEvent implements IClientIncomingPacket
{
	int _eventId = 0;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_eventId = packet.readD();
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
		
		// TODO: UNHARDCODE ME!
		final QuestState qs = player.getQuestState("255_Tutorial");
		if (qs != null)
		{
			qs.getQuest().notifyEvent("CE" + _eventId + "", null, player);
		}
	}
}
