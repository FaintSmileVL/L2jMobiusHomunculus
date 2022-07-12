package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantTwoRemoveOK implements IClientOutgoingPacket
{
	public static final ExEnchantTwoRemoveOK STATIC_PACKET = new ExEnchantTwoRemoveOK();
	
	private ExEnchantTwoRemoveOK()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_TWO_REMOVE_OK.writeId(packet);
		return true;
	}
}
