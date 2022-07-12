package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class RestartResponse implements IClientOutgoingPacket
{
	public static final RestartResponse TRUE = new RestartResponse(true);
	public static final RestartResponse FALSE = new RestartResponse(false);
	
	private final boolean _result;
	
	private RestartResponse(boolean result)
	{
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.RESTART_RESPONSE.writeId(packet);
		packet.writeD(_result ? 1 : 0);
		return true;
	}
}
