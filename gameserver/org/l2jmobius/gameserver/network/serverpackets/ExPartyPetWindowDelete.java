package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExPartyPetWindowDelete implements IClientOutgoingPacket
{
	private final Summon _summon;
	
	public ExPartyPetWindowDelete(Summon summon)
	{
		_summon = summon;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PARTY_PET_WINDOW_DELETE.writeId(packet);
		
		packet.writeD(_summon.getObjectId());
		packet.writeC(_summon.getSummonType());
		packet.writeD(_summon.getOwner().getObjectId());
		return true;
	}
}
