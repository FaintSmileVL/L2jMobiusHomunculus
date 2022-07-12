package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantTwoOK implements IClientOutgoingPacket
{
	public static final ExEnchantTwoOK STATIC_PACKET = new ExEnchantTwoOK();
	
	private ExEnchantTwoOK()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_TWO_OK.writeId(packet);
		return true;
	}
}
