package org.l2jmobius.gameserver.network.serverpackets.training;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExTrainingZone_Leaving implements IClientOutgoingPacket
{
	public static final ExTrainingZone_Leaving STATIC_PACKET = new ExTrainingZone_Leaving();
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TRAINING_ZONE_LEAVING.writeId(packet);
		return true;
	}
}
