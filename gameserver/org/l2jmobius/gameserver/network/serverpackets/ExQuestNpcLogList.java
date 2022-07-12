package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.holders.NpcLogListHolder;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExQuestNpcLogList implements IClientOutgoingPacket
{
	private final int _questId;
	private final List<NpcLogListHolder> _npcLogList = new ArrayList<>();
	
	public ExQuestNpcLogList(int questId)
	{
		_questId = questId;
	}
	
	public void addNpc(int npcId, int count)
	{
		_npcLogList.add(new NpcLogListHolder(npcId, false, count));
	}
	
	public void addNpcString(NpcStringId npcStringId, int count)
	{
		_npcLogList.add(new NpcLogListHolder(npcStringId.getId(), true, count));
	}
	
	public void add(NpcLogListHolder holder)
	{
		_npcLogList.add(holder);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_QUEST_NPC_LOG_LIST.writeId(packet);
		
		packet.writeD(_questId);
		packet.writeC(_npcLogList.size());
		for (NpcLogListHolder holder : _npcLogList)
		{
			packet.writeD(holder.isNpcString() ? holder.getId() : holder.getId() + 1000000);
			packet.writeC(holder.isNpcString() ? 0x01 : 0x00);
			packet.writeD(holder.getCount());
		}
		return true;
	}
}