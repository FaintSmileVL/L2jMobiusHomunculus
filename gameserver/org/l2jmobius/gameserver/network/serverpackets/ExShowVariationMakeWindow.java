package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Trigger packet
 * @author KenM
 */
public class ExShowVariationMakeWindow implements IClientOutgoingPacket
{
	public static final ExShowVariationMakeWindow STATIC_PACKET = new ExShowVariationMakeWindow();
	
	private ExShowVariationMakeWindow()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_VARIATION_MAKE_WINDOW.writeId(packet);
		
		return true;
	}
}
