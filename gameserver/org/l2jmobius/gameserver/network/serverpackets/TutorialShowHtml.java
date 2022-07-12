package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.HtmlActionScope;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * TutorialShowHtml server packet implementation.
 * @author HorridoJoho
 */
public class TutorialShowHtml extends AbstractHtmlPacket
{
	// TODO: Enum
	public static final int NORMAL_WINDOW = 1;
	public static final int LARGE_WINDOW = 2;
	
	private final int _type;
	
	public TutorialShowHtml(String html)
	{
		super(html);
		_type = NORMAL_WINDOW;
	}
	
	public TutorialShowHtml(int npcObjId, String html, int type)
	{
		super(npcObjId, html);
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.TUTORIAL_SHOW_HTML.writeId(packet);
		
		packet.writeD(_type);
		packet.writeS(getHtml());
		return true;
	}
	
	@Override
	public HtmlActionScope getScope()
	{
		return HtmlActionScope.TUTORIAL_HTML;
	}
}
