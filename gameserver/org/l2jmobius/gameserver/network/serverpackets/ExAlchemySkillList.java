package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.data.xml.SkillTreeData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.CommonSkill;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExAlchemySkillList implements IClientOutgoingPacket
{
	private final List<Skill> _skills = new ArrayList<>();
	
	public ExAlchemySkillList(PlayerInstance player)
	{
		for (Skill s : player.getAllSkills())
		{
			if (SkillTreeData.getInstance().isAlchemySkill(s.getId(), s.getLevel()))
			{
				_skills.add(s);
			}
		}
		_skills.add(SkillData.getInstance().getSkill(CommonSkill.ALCHEMY_CUBE.getId(), 1));
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ALCHEMY_SKILL_LIST.writeId(packet);
		
		packet.writeD(_skills.size());
		for (Skill skill : _skills)
		{
			packet.writeD(skill.getId());
			packet.writeD(skill.getLevel());
			packet.writeQ(0x00); // Always 0 on Naia, SP i guess?
			packet.writeC(skill.getId() == CommonSkill.ALCHEMY_CUBE.getId() ? 0 : 1); // This is type in flash, visible or not
		}
		return true;
	}
}
