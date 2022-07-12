package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.SkillTreeData;
import org.l2jmobius.gameserver.model.SkillLearn;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw, Mobius
 */
public class AcquireSkillList implements IClientOutgoingPacket
{
	private PlayerInstance _player;
	private List<SkillLearn> _learnable;
	
	public AcquireSkillList(PlayerInstance player)
	{
		if (!player.isSubclassLocked()) // Changing class.
		{
			_player = player;
			_learnable = SkillTreeData.getInstance().getAvailableSkills(player, player.getClassId(), false, true, false);
			_learnable.addAll(SkillTreeData.getInstance().getNextAvailableSkills(player, player.getClassId(), false, true, false));
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		if (_player == null)
		{
			return false;
		}
		
		OutgoingPackets.ACQUIRE_SKILL_LIST.writeId(packet);
		
		packet.writeH(_learnable.size());
		for (SkillLearn skill : _learnable)
		{
			packet.writeD(skill.getSkillId());
			packet.writeD(skill.getSkillLevel());
			packet.writeQ(skill.getLevelUpSp());
			packet.writeC(skill.getGetLevel());
			packet.writeC(skill.getDualClassLevel());
			packet.writeC(_player.getKnownSkill(skill.getSkillId()) != null ? 0x00 : 0x01);
			packet.writeC(skill.getRequiredItems().size());
			for (ItemHolder item : skill.getRequiredItems())
			{
				packet.writeD(item.getId());
				packet.writeQ(item.getCount());
			}
			final List<Skill> skillRem = skill.getRemoveSkills().stream().map(_player::getKnownSkill).filter(Objects::nonNull).collect(Collectors.toList());
			packet.writeC(skillRem.size());
			for (Skill skillRemove : skillRem)
			{
				packet.writeD(skillRemove.getId());
				packet.writeD(skillRemove.getLevel());
			}
		}
		return true;
	}
}
