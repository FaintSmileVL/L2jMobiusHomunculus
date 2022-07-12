package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Plim
 */
public class PetitionVotePacket implements IClientOutgoingPacket
{
	public static final PetitionVotePacket STATIC_PACKET = new PetitionVotePacket();
	
	private PetitionVotePacket()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PETITION_VOTE.writeId(packet);
		return true;
	}
}
