package org.l2jmobius.gameserver.network.serverpackets.adenadistribution;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExDivideAdenaStart implements IClientOutgoingPacket
{
	public static final ExDivideAdenaStart STATIC_PACKET = new ExDivideAdenaStart();
	
	private ExDivideAdenaStart()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DIVIDE_ADENA_START.writeId(packet);
		return true;
	}
}