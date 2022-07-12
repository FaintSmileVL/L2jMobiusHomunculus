package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class GMViewSkillInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final Collection<Skill> _skills;
	
	public GMViewSkillInfo(PlayerInstance player)
	{
		_player = player;
		_skills = _player.getSkillList();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GM_VIEW_SKILL_INFO.writeId(packet);
		
		packet.writeS(_player.getName());
		packet.writeD(_skills.size());
		
		final boolean isDisabled = (_player.getClan() != null) && (_player.getClan().getReputationScore() < 0);
		for (Skill skill : _skills)
		{
			packet.writeD(skill.isPassive() ? 1 : 0);
			packet.writeH(skill.getDisplayLevel());
			packet.writeH(skill.getSubLevel());
			packet.writeD(skill.getDisplayId());
			packet.writeD(0x00);
			packet.writeC(isDisabled && skill.isClanSkill() ? 1 : 0);
			packet.writeC(skill.isEnchantable() ? 1 : 0);
		}
		return true;
	}
}