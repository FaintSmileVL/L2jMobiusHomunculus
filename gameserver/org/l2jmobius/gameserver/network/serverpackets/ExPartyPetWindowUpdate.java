package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExPartyPetWindowUpdate implements IClientOutgoingPacket
{
	private final Summon _summon;
	
	public ExPartyPetWindowUpdate(Summon summon)
	{
		_summon = summon;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PARTY_PET_WINDOW_UPDATE.writeId(packet);
		
		packet.writeD(_summon.getObjectId());
		packet.writeD(_summon.getTemplate().getDisplayId() + 1000000);
		packet.writeC(_summon.getSummonType());
		packet.writeD(_summon.getOwner().getObjectId());
		packet.writeD((int) _summon.getCurrentHp());
		packet.writeD(_summon.getMaxHp());
		packet.writeD((int) _summon.getCurrentMp());
		packet.writeD(_summon.getMaxMp());
		return true;
	}
}
