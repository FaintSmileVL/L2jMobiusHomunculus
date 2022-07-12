package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantOneOK implements IClientOutgoingPacket
{
	public static final ExEnchantOneOK STATIC_PACKET = new ExEnchantOneOK();
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_ONE_OK.writeId(packet);
		return true;
	}
}
