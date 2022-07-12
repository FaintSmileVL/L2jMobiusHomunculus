package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

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