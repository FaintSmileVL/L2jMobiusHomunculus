package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.HtmlActionScope;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JoeAlisson
 */
public class ExPremiumManagerShowHtml extends AbstractHtmlPacket
{
	public ExPremiumManagerShowHtml(String html)
	{
		super(html);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PREMIUM_MANAGER_SHOW_HTML.writeId(packet);
		packet.writeD(getNpcObjId());
		packet.writeS(getHtml());
		packet.writeD(-1);
		packet.writeD(0);
		return true;
	}
	
	@Override
	public HtmlActionScope getScope()
	{
		return HtmlActionScope.PREMIUM_HTML;
	}
}
