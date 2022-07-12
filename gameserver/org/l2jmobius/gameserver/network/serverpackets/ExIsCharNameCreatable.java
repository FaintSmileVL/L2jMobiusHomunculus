package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExIsCharNameCreatable implements IClientOutgoingPacket
{
	private final int _allowed;
	
	public ExIsCharNameCreatable(int allowed)
	{
		_allowed = allowed;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_IS_CHAR_NAME_CREATABLE.writeId(packet);
		
		packet.writeD(_allowed);
		return true;
	}
}
