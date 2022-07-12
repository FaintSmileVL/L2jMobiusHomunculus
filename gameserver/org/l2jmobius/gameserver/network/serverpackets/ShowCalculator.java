package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShowCalculator implements IClientOutgoingPacket
{
	private final int _calculatorId;
	
	public ShowCalculator(int calculatorId)
	{
		_calculatorId = calculatorId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOW_CALC.writeId(packet);
		
		packet.writeD(_calculatorId);
		return true;
	}
}