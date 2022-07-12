package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class ExTutorialShowId implements IClientOutgoingPacket
{
	private final int _id;
	
	public ExTutorialShowId(int id)
	{
		_id = id;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TUTORIAL_SHOW_ID.writeId(packet);
		packet.writeD(_id);
		return true;
	}
}