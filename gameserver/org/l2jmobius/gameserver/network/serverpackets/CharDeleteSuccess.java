package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class CharDeleteSuccess implements IClientOutgoingPacket
{
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CHARACTER_DELETE_SUCCESS.writeId(packet);
		return true;
	}
}
