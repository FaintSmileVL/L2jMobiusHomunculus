package org.l2jmobius.gameserver.network.serverpackets.ensoul;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExShowEnsoulWindow implements IClientOutgoingPacket
{
	public static final ExShowEnsoulWindow STATIC_PACKET = new ExShowEnsoulWindow();
	
	private ExShowEnsoulWindow()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_ENSOUL_WINDOW.writeId(packet);
		return true;
	}
}
