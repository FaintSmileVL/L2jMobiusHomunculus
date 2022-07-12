package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class TutorialEnableClientEvent implements IClientOutgoingPacket
{
	private int _eventId = 0;
	
	public TutorialEnableClientEvent(int event)
	{
		_eventId = event;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.TUTORIAL_ENABLE_CLIENT_EVENT.writeId(packet);
		
		packet.writeD(_eventId);
		return true;
	}
}
