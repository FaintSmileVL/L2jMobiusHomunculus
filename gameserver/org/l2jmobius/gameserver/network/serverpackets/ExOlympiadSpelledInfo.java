package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author godson
 */
public class ExOlympiadSpelledInfo implements IClientOutgoingPacket
{
	private final int _playerId;
	private final List<BuffInfo> _effects = new ArrayList<>();
	private final List<Skill> _effects2 = new ArrayList<>();
	
	public ExOlympiadSpelledInfo(PlayerInstance player)
	{
		_playerId = player.getObjectId();
	}
	
	public void addSkill(BuffInfo info)
	{
		_effects.add(info);
	}
	
	public void addSkill(Skill skill)
	{
		_effects2.add(skill);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_OLYMPIAD_SPELLED_INFO.writeId(packet);
		
		packet.writeD(_playerId);
		packet.writeD(_effects.size() + _effects2.size());
		for (BuffInfo info : _effects)
		{
			if ((info != null) && info.isInUse())
			{
				packet.writeD(info.getSkill().getDisplayId());
				packet.writeH(info.getSkill().getDisplayLevel());
				packet.writeH(0x00); // Sub level
				packet.writeD(info.getSkill().getAbnormalType().getClientId());
				writeOptionalD(packet, info.getSkill().isAura() ? -1 : info.getTime());
			}
		}
		for (Skill skill : _effects2)
		{
			if (skill != null)
			{
				packet.writeD(skill.getDisplayId());
				packet.writeH(skill.getDisplayLevel());
				packet.writeH(0x00); // Sub level
				packet.writeD(skill.getAbnormalType().getClientId());
				packet.writeH(-1);
			}
		}
		return true;
	}
}
