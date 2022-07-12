package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.AcquireSkillType;
import org.l2jmobius.gameserver.model.SkillLearn;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExAcquirableSkillListByClass implements IClientOutgoingPacket
{
	final List<SkillLearn> _learnable;
	final AcquireSkillType _type;
	
	public ExAcquirableSkillListByClass(List<SkillLearn> learnable, AcquireSkillType type)
	{
		_learnable = learnable;
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ACQUIRABLE_SKILL_LIST_BY_CLASS.writeId(packet);
		
		packet.writeH(_type.getId());
		packet.writeH(_learnable.size());
		for (SkillLearn skill : _learnable)
		{
			packet.writeD(skill.getSkillId());
			packet.writeH(skill.getSkillLevel());
			packet.writeH(skill.getSkillLevel());
			packet.writeC(skill.getGetLevel());
			packet.writeQ(skill.getLevelUpSp());
			packet.writeC(skill.getRequiredItems().size());
			if (_type == AcquireSkillType.SUBPLEDGE)
			{
				packet.writeH(0x00);
			}
		}
		return true;
	}
}
