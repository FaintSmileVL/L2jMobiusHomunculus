package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class CSShowComBoard implements IClientOutgoingPacket
{
	private final byte[] _html;
	
	public CSShowComBoard(byte[] html)
	{
		_html = html;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOW_BOARD.writeId(packet);
		
		packet.writeC(0x01); // c4 1 to show community 00 to hide
		packet.writeB(_html);
		return true;
	}
}
