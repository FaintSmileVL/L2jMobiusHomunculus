package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PartySmallWindowAdd implements IClientOutgoingPacket
{
	private final PlayerInstance _member;
	private final Party _party;
	
	public PartySmallWindowAdd(PlayerInstance member, Party party)
	{
		_member = member;
		_party = party;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PARTY_SMALL_WINDOW_ADD.writeId(packet);
		
		packet.writeD(_party.getLeaderObjectId()); // c3
		packet.writeD(_party.getDistributionType().getId()); // c3
		packet.writeD(_member.getObjectId());
		packet.writeS(_member.getName());
		
		packet.writeD((int) _member.getCurrentCp()); // c4
		packet.writeD(_member.getMaxCp()); // c4
		packet.writeD((int) _member.getCurrentHp());
		packet.writeD(_member.getMaxHp());
		packet.writeD((int) _member.getCurrentMp());
		packet.writeD(_member.getMaxMp());
		packet.writeD(_member.getVitalityPoints());
		packet.writeC(_member.getLevel());
		packet.writeH(_member.getClassId().getId());
		packet.writeC(0x00);
		packet.writeH(_member.getRace().ordinal());
		packet.writeD(0x00); // 228
		return true;
	}
}
