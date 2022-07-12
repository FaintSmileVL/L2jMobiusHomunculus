package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class PledgeSkillList implements IClientOutgoingPacket
{
	private final Skill[] _skills;
	private final SubPledgeSkill[] _subSkills;
	
	public static class SubPledgeSkill
	{
		int _subType;
		int _skillId;
		int _skillLevel;
		
		public SubPledgeSkill(int subType, int skillId, int skillLevel)
		{
			_subType = subType;
			_skillId = skillId;
			_skillLevel = skillLevel;
		}
	}
	
	public PledgeSkillList(Clan clan)
	{
		_skills = clan.getAllSkills();
		_subSkills = clan.getAllSubSkills();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SKILL_LIST.writeId(packet);
		
		packet.writeD(_skills.length);
		packet.writeD(_subSkills.length); // Squad skill length
		for (Skill sk : _skills)
		{
			packet.writeD(sk.getDisplayId());
			packet.writeH(sk.getDisplayLevel());
			packet.writeH(0x00); // Sub level
		}
		for (SubPledgeSkill sk : _subSkills)
		{
			packet.writeD(sk._subType); // Clan Sub-unit types
			packet.writeD(sk._skillId);
			packet.writeH(sk._skillLevel);
			packet.writeH(0x00); // Sub level
		}
		return true;
	}
}
