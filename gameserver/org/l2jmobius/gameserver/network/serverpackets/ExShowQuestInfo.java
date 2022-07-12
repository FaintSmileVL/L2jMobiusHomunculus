package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Luca Baldi
 */
public class ExShowQuestInfo implements IClientOutgoingPacket
{
	public static final ExShowQuestInfo STATIC_PACKET = new ExShowQuestInfo();
	
	private ExShowQuestInfo()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_QUEST_INFO.writeId(packet);
		
		return true;
	}
}
