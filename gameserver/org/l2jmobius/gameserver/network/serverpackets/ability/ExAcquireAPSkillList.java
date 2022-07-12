package org.l2jmobius.gameserver.network.serverpackets.ability;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.SkillTreeData;
import org.l2jmobius.gameserver.model.SkillLearn;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExAcquireAPSkillList implements IClientOutgoingPacket
{
	private final int _abilityPoints;
	private final int _usedAbilityPoints;
	// private final long _price; Removed on Grand Crusade
	private final boolean _enable;
	private final List<Skill> _skills = new ArrayList<>();
	
	public ExAcquireAPSkillList(PlayerInstance player)
	{
		_abilityPoints = player.getAbilityPoints();
		_usedAbilityPoints = player.getAbilityPointsUsed();
		// _price = AbilityPointsData.getInstance().getPrice(_abilityPoints); Removed on Grand Crusade
		for (SkillLearn sk : SkillTreeData.getInstance().getAbilitySkillTree().values())
		{
			final Skill knownSkill = player.getKnownSkill(sk.getSkillId());
			if ((knownSkill != null) && (knownSkill.getLevel() == sk.getSkillLevel()))
			{
				_skills.add(knownSkill);
			}
		}
		_enable = (!player.isSubClassActive() || player.isDualClassActive()) && (player.getLevel() >= 85);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ACQUIRE_AP_SKILL_LIST.writeId(packet);
		
		packet.writeD(_enable ? 1 : 0);
		packet.writeQ(Config.ABILITY_POINTS_RESET_SP); // Changed to from Adena to SP on Grand Crusade
		// packet.writeQ(_price); Removed on Grand Crusade
		// packet.writeD(Config.ABILITY_MAX_POINTS); Removed on Grand Crusade
		packet.writeD(_abilityPoints);
		packet.writeD(_usedAbilityPoints);
		packet.writeD(_skills.size());
		for (Skill skill : _skills)
		{
			packet.writeD(skill.getId());
			packet.writeD(skill.getLevel());
		}
		return true;
	}
}