package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Format: (ch)ddd
 */
public class ExVariationResult implements IClientOutgoingPacket
{
	private final int _option1;
	private final int _option2;
	private final int _success;
	
	public ExVariationResult(int option1, int option2, boolean success)
	{
		_option1 = option1;
		_option2 = option2;
		_success = success ? 0x01 : 0x00;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_VARIATION_RESULT.writeId(packet);
		
		packet.writeD(_option1);
		packet.writeD(_option2);
		packet.writeD(_success);
		return true;
	}
}
