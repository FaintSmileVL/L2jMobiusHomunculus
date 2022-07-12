package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExShowVariationCancelWindow implements IClientOutgoingPacket
{
	public static final ExShowVariationCancelWindow STATIC_PACKET = new ExShowVariationCancelWindow();
	
	private ExShowVariationCancelWindow()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_VARIATION_CANCEL_WINDOW.writeId(packet);
		
		return true;
	}
}
