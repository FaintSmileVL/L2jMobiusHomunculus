package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.QuestType;
import org.l2jmobius.gameserver.instancemanager.QuestManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.Containers;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerQuestAbort;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.QuestList;

/**
 * @version $Revision: 1.3.4.2 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestQuestAbort implements IClientIncomingPacket
{
	private int _questId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_questId = packet.readD();
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
		
		final Quest qe = QuestManager.getInstance().getQuest(_questId);
		if (qe != null)
		{
			final QuestState qs = player.getQuestState(qe.getName());
			if (qs != null)
			{
				qs.setSimulated(false);
				qs.exitQuest(QuestType.REPEATABLE);
				player.sendPacket(new QuestList(player));
				EventDispatcher.getInstance().notifyEventAsync(new OnPlayerQuestAbort(player, _questId), player, Containers.Players());
				qe.onQuestAborted(player);
			}
		}
	}
}