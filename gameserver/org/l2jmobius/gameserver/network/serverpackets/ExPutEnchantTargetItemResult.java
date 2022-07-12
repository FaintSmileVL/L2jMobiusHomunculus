package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author nBd
 */
public class ExPutEnchantTargetItemResult implements IClientOutgoingPacket
{
	private final int _result;
	
	public ExPutEnchantTargetItemResult(int result)
	{
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_ENCHANT_TARGET_ITEM_RESULT.writeId(packet);
		
		packet.writeD(_result);
		return true;
	}
}
