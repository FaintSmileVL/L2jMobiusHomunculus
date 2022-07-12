package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Tempy
 */
public class GmViewQuestInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final List<Quest> _questList;
	
	public GmViewQuestInfo(PlayerInstance player)
	{
		_player = player;
		_questList = player.getAllActiveQuests();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GM_VIEW_QUEST_INFO.writeId(packet);
		packet.writeS(_player.getName());
		packet.writeH(_questList.size()); // quest count
		
		for (Quest quest : _questList)
		{
			final QuestState qs = _player.getQuestState(quest.getName());
			packet.writeD(quest.getId());
			packet.writeD(qs == null ? 0 : qs.getCond());
		}
		packet.writeH(0x00); // some size
		// for size; ddQQ
		return true;
	}
}
