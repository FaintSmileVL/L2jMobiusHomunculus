package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.CharacterDeleteFailType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class CharDeleteFail implements IClientOutgoingPacket
{
	private final int _error;
	
	public CharDeleteFail(CharacterDeleteFailType type)
	{
		_error = type.ordinal();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHARACTER_DELETE_FAIL.writeId(packet);
		
		packet.writeD(_error);
		return true;
	}
}
