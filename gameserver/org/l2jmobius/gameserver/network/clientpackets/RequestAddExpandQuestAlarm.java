package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.QuestManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Sdw
 */
public class RequestAddExpandQuestAlarm implements IClientIncomingPacket
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
		
		final Quest quest = QuestManager.getInstance().getQuest(_questId);
		if (quest != null)
		{
			quest.sendNpcLogList(player);
		}
	}
}
