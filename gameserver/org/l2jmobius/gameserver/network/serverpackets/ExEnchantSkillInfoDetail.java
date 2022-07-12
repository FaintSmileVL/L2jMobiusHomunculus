package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.EnchantSkillGroupsData;
import org.l2jmobius.gameserver.enums.SkillEnchantType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.EnchantSkillHolder;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExEnchantSkillInfoDetail implements IClientOutgoingPacket
{
	private final SkillEnchantType _type;
	private final int _skillId;
	private final int _skillLevel;
	private final int _skillSubLevel;
	private final EnchantSkillHolder _enchantSkillHolder;
	
	public ExEnchantSkillInfoDetail(SkillEnchantType type, int skillId, int skillLevel, int skillSubLevel, PlayerInstance player)
	{
		_type = type;
		_skillId = skillId;
		_skillLevel = skillLevel;
		_skillSubLevel = skillSubLevel;
		_enchantSkillHolder = EnchantSkillGroupsData.getInstance().getEnchantSkillHolder(skillSubLevel % 1000);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_SKILL_INFO_DETAIL.writeId(packet);
		
		packet.writeD(_type.ordinal());
		packet.writeD(_skillId);
		packet.writeH(_skillLevel);
		packet.writeH(_skillSubLevel);
		if (_enchantSkillHolder != null)
		{
			packet.writeQ(_enchantSkillHolder.getSp(_type));
			packet.writeD(_enchantSkillHolder.getChance(_type));
			final Set<ItemHolder> holders = _enchantSkillHolder.getRequiredItems(_type);
			packet.writeD(holders.size());
			holders.forEach(holder ->
			{
				packet.writeD(holder.getId());
				packet.writeD((int) holder.getCount());
			});
		}
		return _enchantSkillHolder != null;
	}
}
