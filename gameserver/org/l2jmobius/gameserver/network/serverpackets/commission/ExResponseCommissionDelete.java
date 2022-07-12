package org.l2jmobius.gameserver.network.serverpackets.commission;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author NosBit
 */
public class ExResponseCommissionDelete implements IClientOutgoingPacket
{
	public static final ExResponseCommissionDelete SUCCEED = new ExResponseCommissionDelete(1);
	public static final ExResponseCommissionDelete FAILED = new ExResponseCommissionDelete(0);
	
	private final int _result;
	
	private ExResponseCommissionDelete(int result)
	{
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_COMMISSION_DELETE.writeId(packet);
		
		packet.writeD(_result);
		return true;
	}
}
