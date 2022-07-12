package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class Ex2ndPasswordAck implements IClientOutgoingPacket
{
	private final int _status;
	private final int _response;
	
	// TODO: Enum
	public static final int SUCCESS = 0x00;
	public static final int WRONG_PATTERN = 0x01;
	
	public Ex2ndPasswordAck(int status, int response)
	{
		_status = status;
		_response = response;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_2ND_PASSWORD_ACK.writeId(packet);
		
		packet.writeC(_status);
		packet.writeD(_response == WRONG_PATTERN ? 0x01 : 0x00);
		packet.writeD(0x00);
		return true;
	}
}
