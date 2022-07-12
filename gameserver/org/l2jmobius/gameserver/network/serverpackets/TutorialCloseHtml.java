package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * TutorialCloseHtml server packet implementation.
 * @author HorridoJoho
 */
public class TutorialCloseHtml implements IClientOutgoingPacket
{
	public static final TutorialCloseHtml STATIC_PACKET = new TutorialCloseHtml();
	
	private TutorialCloseHtml()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.TUTORIAL_CLOSE_HTML.writeId(packet);
		return true;
	}
}
