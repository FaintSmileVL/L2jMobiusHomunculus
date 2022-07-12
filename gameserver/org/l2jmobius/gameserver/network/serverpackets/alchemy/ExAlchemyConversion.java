package org.l2jmobius.gameserver.network.serverpackets.alchemy;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExAlchemyConversion implements IClientOutgoingPacket
{
	private final int _successCount;
	private final int _failureCount;
	
	public ExAlchemyConversion(int successCount, int failureCount)
	{
		_successCount = successCount;
		_failureCount = failureCount;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ALCHEMY_CONVERSION.writeId(packet);
		
		packet.writeC((_successCount == 0) && (_failureCount == 0) ? 0x01 : 0x00);
		packet.writeD(_successCount);
		packet.writeD(_failureCount);
		return true;
	}
}
