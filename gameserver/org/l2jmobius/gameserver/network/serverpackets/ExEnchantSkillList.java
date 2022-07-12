package org.l2jmobius.gameserver.network.serverpackets;

import java.util.LinkedList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.SkillEnchantType;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExEnchantSkillList implements IClientOutgoingPacket
{
	private final SkillEnchantType _type;
	private final List<Skill> _skills = new LinkedList<>();
	
	public ExEnchantSkillList(SkillEnchantType type)
	{
		_type = type;
	}
	
	public void addSkill(Skill skill)
	{
		_skills.add(skill);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_SKILL_LIST.writeId(packet);
		
		packet.writeD(_type.ordinal());
		packet.writeD(_skills.size());
		for (Skill skill : _skills)
		{
			packet.writeD(skill.getId());
			packet.writeH(skill.getLevel());
			packet.writeH(skill.getSubLevel());
		}
		return true;
	}
}