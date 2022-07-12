package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author chris_00
 */
public class ExMPCCShowPartyMemberInfo implements IClientOutgoingPacket
{
	private final Party _party;
	
	public ExMPCCShowPartyMemberInfo(Party party)
	{
		_party = party;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MPCCSHOW_PARTY_MEMBER_INFO.writeId(packet);
		
		packet.writeD(_party.getMemberCount());
		for (PlayerInstance pc : _party.getMembers())
		{
			packet.writeS(pc.getName());
			packet.writeD(pc.getObjectId());
			packet.writeD(pc.getClassId().getId());
		}
		return true;
	}
}
