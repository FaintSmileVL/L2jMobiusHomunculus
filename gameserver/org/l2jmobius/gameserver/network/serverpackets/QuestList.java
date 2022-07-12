package org.l2jmobius.gameserver.network.serverpackets;

import java.util.LinkedList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class QuestList implements IClientOutgoingPacket
{
	private final List<QuestState> _activeQuests;
	private final byte[] _oneTimeQuestMask;
	
	public QuestList(PlayerInstance player)
	{
		_activeQuests = new LinkedList<>();
		_oneTimeQuestMask = new byte[128];
		for (QuestState qs : player.getAllQuestStates())
		{
			final int questId = qs.getQuest().getId();
			if (questId > 0)
			{
				if (qs.isStarted())
				{
					_activeQuests.add(qs);
				}
				else if (qs.isCompleted() && !(((questId > 255) && (questId < 10256)) || (questId > 11023)))
				{
					_oneTimeQuestMask[(questId % 10000) / 8] |= 1 << (questId % 8);
				}
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.QUEST_LIST.writeId(packet);
		packet.writeH(_activeQuests.size());
		for (QuestState qs : _activeQuests)
		{
			packet.writeD(qs.getQuest().getId());
			packet.writeD(qs.getCond());
		}
		packet.writeB(_oneTimeQuestMask);
		return true;
	}
}
