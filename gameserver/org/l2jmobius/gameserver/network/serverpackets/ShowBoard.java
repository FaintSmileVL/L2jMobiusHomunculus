package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShowBoard implements IClientOutgoingPacket
{
	private final String _content;
	private int _showBoard = 1; // 1 show, 0 hide
	
	public ShowBoard(String htmlCode, String id)
	{
		_content = id + "\u0008" + htmlCode;
	}
	
	/**
	 * Hides the community board
	 */
	public ShowBoard()
	{
		_showBoard = 0;
		_content = "";
	}
	
	public ShowBoard(List<String> arg)
	{
		final StringBuilder builder = new StringBuilder(256).append("1002\u0008");
		for (String str : arg)
		{
			builder.append(str).append("\u0008");
		}
		_content = builder.toString();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOW_BOARD.writeId(packet);
		
		packet.writeC(_showBoard); // c4 1 to show community 00 to hide
		packet.writeS("bypass _bbshome"); // top
		packet.writeS("bypass _bbsgetfav"); // favorite
		packet.writeS("bypass _bbsloc"); // region
		packet.writeS("bypass _bbsclan"); // clan
		packet.writeS("bypass _bbsmemo"); // memo
		packet.writeS("bypass _bbsmail"); // mail
		packet.writeS("bypass _bbsfriends"); // friends
		packet.writeS("bypass bbs_add_fav"); // add fav.
		packet.writeS(_content);
		return true;
	}
}
