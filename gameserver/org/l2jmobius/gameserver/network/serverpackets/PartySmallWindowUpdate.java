package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.PartySmallWindowUpdateType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PartySmallWindowUpdate extends AbstractMaskPacket<PartySmallWindowUpdateType>
{
	private final PlayerInstance _member;
	private int _flags = 0;
	
	public PartySmallWindowUpdate(PlayerInstance member, boolean addAllFlags)
	{
		_member = member;
		if (addAllFlags)
		{
			for (PartySmallWindowUpdateType type : PartySmallWindowUpdateType.values())
			{
				addComponentType(type);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PARTY_SMALL_WINDOW_UPDATE.writeId(packet);
		
		packet.writeD(_member.getObjectId());
		packet.writeH(_flags);
		if (containsMask(PartySmallWindowUpdateType.CURRENT_CP))
		{
			packet.writeD((int) _member.getCurrentCp()); // c4
		}
		if (containsMask(PartySmallWindowUpdateType.MAX_CP))
		{
			packet.writeD(_member.getMaxCp()); // c4
		}
		if (containsMask(PartySmallWindowUpdateType.CURRENT_HP))
		{
			packet.writeD((int) _member.getCurrentHp());
		}
		if (containsMask(PartySmallWindowUpdateType.MAX_HP))
		{
			packet.writeD(_member.getMaxHp());
		}
		if (containsMask(PartySmallWindowUpdateType.CURRENT_MP))
		{
			packet.writeD((int) _member.getCurrentMp());
		}
		if (containsMask(PartySmallWindowUpdateType.MAX_MP))
		{
			packet.writeD(_member.getMaxMp());
		}
		if (containsMask(PartySmallWindowUpdateType.LEVEL))
		{
			packet.writeC(_member.getLevel());
		}
		if (containsMask(PartySmallWindowUpdateType.CLASS_ID))
		{
			packet.writeH(_member.getClassId().getId());
		}
		if (containsMask(PartySmallWindowUpdateType.PARTY_SUBSTITUTE))
		{
			packet.writeC(0x00);
		}
		if (containsMask(PartySmallWindowUpdateType.VITALITY_POINTS))
		{
			packet.writeD(_member.getVitalityPoints());
		}
		return true;
	}
	
	@Override
	protected void addMask(int mask)
	{
		_flags |= mask;
	}
	
	@Override
	public boolean containsMask(PartySmallWindowUpdateType component)
	{
		return containsMask(_flags, component);
	}
	
	@Override
	protected byte[] getMasks()
	{
		return new byte[0];
	}
}
