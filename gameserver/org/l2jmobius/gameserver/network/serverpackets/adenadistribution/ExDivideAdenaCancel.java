package org.l2jmobius.gameserver.network.serverpackets.adenadistribution;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExDivideAdenaCancel implements IClientOutgoingPacket
{
	public static final ExDivideAdenaCancel STATIC_PACKET = new ExDivideAdenaCancel();
	
	private ExDivideAdenaCancel()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DIVIDE_ADENA_CANCEL.writeId(packet);
		
		packet.writeC(0x00); // TODO: Find me
		return true;
	}
}
