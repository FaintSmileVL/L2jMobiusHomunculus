package org.l2jmobius.gameserver.network.serverpackets.ability;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExShowAPListWnd implements IClientOutgoingPacket
{
	public static final ExShowAPListWnd STATIC_PACKET = new ExShowAPListWnd();
	
	private ExShowAPListWnd()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_AP_LIST_WND.writeId(packet);
		
		return true;
	}
}