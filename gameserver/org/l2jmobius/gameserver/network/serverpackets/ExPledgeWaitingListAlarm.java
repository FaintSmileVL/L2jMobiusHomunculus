package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeWaitingListAlarm implements IClientOutgoingPacket
{
	public static final ExPledgeWaitingListAlarm STATIC_PACKET = new ExPledgeWaitingListAlarm();
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_WAITING_LIST_ALARM.writeId(packet);
		
		return true;
	}
}
