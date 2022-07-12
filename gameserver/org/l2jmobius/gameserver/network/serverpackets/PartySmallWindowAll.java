package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PartySmallWindowAll implements IClientOutgoingPacket
{
	private final Party _party;
	private final PlayerInstance _exclude;
	
	public PartySmallWindowAll(PlayerInstance exclude, Party party)
	{
		_exclude = exclude;
		_party = party;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PARTY_SMALL_WINDOW_ALL.writeId(packet);
		
		packet.writeD(_party.getLeaderObjectId());
		packet.writeC(_party.getDistributionType().getId());
		packet.writeC(_party.getMemberCount() - 1);
		
		for (PlayerInstance member : _party.getMembers())
		{
			if ((member != null) && (member != _exclude))
			{
				packet.writeD(member.getObjectId());
				packet.writeS(member.getName());
				
				packet.writeD((int) member.getCurrentCp()); // c4
				packet.writeD(member.getMaxCp()); // c4
				
				packet.writeD((int) member.getCurrentHp());
				packet.writeD(member.getMaxHp());
				packet.writeD((int) member.getCurrentMp());
				packet.writeD(member.getMaxMp());
				packet.writeD(member.getVitalityPoints());
				packet.writeC(member.getLevel());
				packet.writeH(member.getClassId().getId());
				packet.writeC(0x01); // Unk
				packet.writeH(member.getRace().ordinal());
				packet.writeD(0x00); // 228
				
				final Summon pet = member.getPet();
				packet.writeD(member.getServitors().size() + (pet != null ? 1 : 0)); // Summon size, one only atm
				if (pet != null)
				{
					packet.writeD(pet.getObjectId());
					packet.writeD(pet.getId() + 1000000);
					packet.writeC(pet.getSummonType());
					packet.writeS(pet.getName());
					packet.writeD((int) pet.getCurrentHp());
					packet.writeD(pet.getMaxHp());
					packet.writeD((int) pet.getCurrentMp());
					packet.writeD(pet.getMaxMp());
					packet.writeC(pet.getLevel());
				}
				member.getServitors().values().forEach(s ->
				{
					packet.writeD(s.getObjectId());
					packet.writeD(s.getId() + 1000000);
					packet.writeC(s.getSummonType());
					packet.writeS(s.getName());
					packet.writeD((int) s.getCurrentHp());
					packet.writeD(s.getMaxHp());
					packet.writeD((int) s.getCurrentMp());
					packet.writeD(s.getMaxMp());
					packet.writeC(s.getLevel());
				});
			}
		}
		return true;
	}
}
