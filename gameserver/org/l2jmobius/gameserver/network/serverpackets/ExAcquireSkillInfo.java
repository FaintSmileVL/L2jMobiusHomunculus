package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.SkillLearn;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExAcquireSkillInfo implements IClientOutgoingPacket
{
	private final int _id;
	private final int _level;
	private final int _dualClassLevel;
	private final long _spCost;
	private final int _minLevel;
	private final List<ItemHolder> _itemReq;
	private final List<Skill> _skillRem;
	
	/**
	 * Special constructor for Alternate Skill Learning system.<br>
	 * Sets a custom amount of SP.
	 * @param player
	 * @param skillLearn the skill learn.
	 */
	public ExAcquireSkillInfo(PlayerInstance player, SkillLearn skillLearn)
	{
		_id = skillLearn.getSkillId();
		_level = skillLearn.getSkillLevel();
		_dualClassLevel = skillLearn.getDualClassLevel();
		_spCost = skillLearn.getLevelUpSp();
		_minLevel = skillLearn.getGetLevel();
		_itemReq = skillLearn.getRequiredItems();
		_skillRem = skillLearn.getRemoveSkills().stream().map(player::getKnownSkill).filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ACQUIRE_SKILL_INFO.writeId(packet);
		
		packet.writeD(_id);
		packet.writeD(_level);
		packet.writeQ(_spCost);
		packet.writeH(_minLevel);
		packet.writeH(_dualClassLevel);
		packet.writeD(_itemReq.size());
		for (ItemHolder holder : _itemReq)
		{
			packet.writeD(holder.getId());
			packet.writeQ(holder.getCount());
		}
		
		packet.writeD(_skillRem.size());
		for (Skill skill : _skillRem)
		{
			packet.writeD(skill.getId());
			packet.writeD(skill.getLevel());
		}
		return true;
	}
}
