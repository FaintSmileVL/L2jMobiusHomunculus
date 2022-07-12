package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PartySmallWindowDelete implements IClientOutgoingPacket
{
	private final PlayerInstance _member;
	
	public PartySmallWindowDelete(PlayerInstance member)
	{
		_member = member;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PARTY_SMALL_WINDOW_DELETE.writeId(packet);
		
		packet.writeD(_member.getObjectId());
		packet.writeS(_member.getName());
		return true;
	}
}
