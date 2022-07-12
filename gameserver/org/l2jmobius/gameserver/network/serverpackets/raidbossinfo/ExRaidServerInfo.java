package org.l2jmobius.gameserver.network.serverpackets.raidbossinfo;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExRaidServerInfo implements IClientOutgoingPacket
{
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RAID_SERVER_INFO.writeId(packet);
		return true;
	}
}
