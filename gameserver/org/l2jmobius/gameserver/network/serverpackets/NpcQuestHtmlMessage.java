package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.HtmlActionScope;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * NpcQuestHtmlMessage server packet implementation.
 * @author HorridoJoho
 */
public class NpcQuestHtmlMessage extends AbstractHtmlPacket
{
	private final int _questId;
	
	public NpcQuestHtmlMessage(int npcObjId, int questId)
	{
		super(npcObjId);
		_questId = questId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_NPC_QUEST_HTML_MESSAGE.writeId(packet);
		
		packet.writeD(getNpcObjId());
		packet.writeS(getHtml());
		packet.writeD(_questId);
		return true;
	}
	
	@Override
	public HtmlActionScope getScope()
	{
		return HtmlActionScope.NPC_QUEST_HTML;
	}
}
