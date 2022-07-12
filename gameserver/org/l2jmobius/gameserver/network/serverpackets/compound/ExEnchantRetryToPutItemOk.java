package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExEnchantRetryToPutItemOk implements IClientOutgoingPacket
{
	public static final ExEnchantRetryToPutItemOk STATIC_PACKET = new ExEnchantRetryToPutItemOk();
	
	private ExEnchantRetryToPutItemOk()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_RETRY_TO_PUT_ITEM_OK.writeId(packet);
		return true;
	}
}