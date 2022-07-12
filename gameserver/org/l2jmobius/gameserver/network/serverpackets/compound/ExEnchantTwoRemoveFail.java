package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantTwoRemoveFail implements IClientOutgoingPacket
{
	public static final ExEnchantTwoRemoveFail STATIC_PACKET = new ExEnchantTwoRemoveFail();
	
	private ExEnchantTwoRemoveFail()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_TWO_REMOVE_FAIL.writeId(packet);
		return true;
	}
}
