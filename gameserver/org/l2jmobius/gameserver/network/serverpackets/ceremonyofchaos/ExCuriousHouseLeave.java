package org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExCuriousHouseLeave implements IClientOutgoingPacket
{
	public static final ExCuriousHouseLeave STATIC_PACKET = new ExCuriousHouseLeave();
	
	private ExCuriousHouseLeave()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CURIOUS_HOUSE_LEAVE.writeId(packet);
		return true;
	}
}
