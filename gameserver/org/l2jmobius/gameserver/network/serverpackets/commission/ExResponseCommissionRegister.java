package org.l2jmobius.gameserver.network.serverpackets.commission;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author NosBit
 */
public class ExResponseCommissionRegister implements IClientOutgoingPacket
{
	public static final ExResponseCommissionRegister SUCCEED = new ExResponseCommissionRegister(1);
	public static final ExResponseCommissionRegister FAILED = new ExResponseCommissionRegister(0);
	
	private final int _result;
	
	private ExResponseCommissionRegister(int result)
	{
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_COMMISSION_REGISTER.writeId(packet);
		
		packet.writeD(_result);
		return true;
	}
}
