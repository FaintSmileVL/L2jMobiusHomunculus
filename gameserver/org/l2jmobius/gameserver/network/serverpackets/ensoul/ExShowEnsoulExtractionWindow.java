package org.l2jmobius.gameserver.network.serverpackets.ensoul;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExShowEnsoulExtractionWindow implements IClientOutgoingPacket
{
	public static final ExShowEnsoulExtractionWindow STATIC_PACKET = new ExShowEnsoulExtractionWindow();
	
	private ExShowEnsoulExtractionWindow()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENSOUL_EXTRACTION_SHOW.writeId(packet);
		return true;
	}
}