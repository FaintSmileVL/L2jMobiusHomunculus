package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantOneRemoveOK implements IClientOutgoingPacket
{
	public static final ExEnchantOneRemoveOK STATIC_PACKET = new ExEnchantOneRemoveOK();
	
	private ExEnchantOneRemoveOK()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_ONE_REMOVE_OK.writeId(packet);
		return true;
	}
}
