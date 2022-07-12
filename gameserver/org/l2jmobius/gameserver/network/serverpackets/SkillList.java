package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SkillList implements IClientOutgoingPacket
{
	private final List<Skill> _skills = new ArrayList<>();
	private int _lastLearnedSkillId = 0;
	
	static class Skill
	{
		public int id;
		public int reuseDelayGroup;
		public int level;
		public int subLevel;
		public boolean passive;
		public boolean disabled;
		public boolean enchanted;
		
		Skill(int pId, int pReuseDelayGroup, int pLevel, int pSubLevel, boolean pPassive, boolean pDisabled, boolean pEnchanted)
		{
			id = pId;
			reuseDelayGroup = pReuseDelayGroup;
			level = pLevel;
			subLevel = pSubLevel;
			passive = pPassive;
			disabled = pDisabled;
			enchanted = pEnchanted;
		}
	}
	
	public void addSkill(int id, int reuseDelayGroup, int level, int subLevel, boolean passive, boolean disabled, boolean enchanted)
	{
		_skills.add(new Skill(id, reuseDelayGroup, level, subLevel, passive, disabled, enchanted));
	}
	
	public void setLastLearnedSkillId(int lastLearnedSkillId)
	{
		_lastLearnedSkillId = lastLearnedSkillId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SKILL_LIST.writeId(packet);
		_skills.sort(Comparator.comparing(s -> SkillData.getInstance().getSkill(s.id, s.level, s.subLevel).isToggle() ? 1 : 0));
		packet.writeD(_skills.size());
		for (Skill temp : _skills)
		{
			packet.writeD(temp.passive ? 1 : 0);
			packet.writeH(temp.level);
			packet.writeH(temp.subLevel);
			packet.writeD(temp.id);
			packet.writeD(temp.reuseDelayGroup); // GOD ReuseDelayShareGroupID
			packet.writeC(temp.disabled ? 1 : 0); // iSkillDisabled
			packet.writeC(temp.enchanted ? 1 : 0); // CanEnchant
		}
		packet.writeD(_lastLearnedSkillId);
		return true;
	}
}
