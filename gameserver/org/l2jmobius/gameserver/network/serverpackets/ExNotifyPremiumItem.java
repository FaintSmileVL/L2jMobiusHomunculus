package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class ExNotifyPremiumItem implements IClientOutgoingPacket
{
	public static final ExNotifyPremiumItem STATIC_PACKET = new ExNotifyPremiumItem();
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_NOTIFY_PREMIUM_ITEM.writeId(packet);
		
		return true;
	}
}
