package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 **/
public class ExBirthdayPopup implements IClientOutgoingPacket
{
	public static final ExBirthdayPopup STATIC_PACKET = new ExBirthdayPopup();
	
	private ExBirthdayPopup()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_NOTIFY_BIRTH_DAY.writeId(packet);
		
		return true;
	}
}
