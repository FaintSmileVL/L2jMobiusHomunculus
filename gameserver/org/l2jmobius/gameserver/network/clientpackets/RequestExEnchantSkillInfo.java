package org.l2jmobius.gameserver.network.clientpackets;

import java.util.Set;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.EnchantSkillGroupsData;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExEnchantSkillInfo;

/**
 * Format (ch) dd c: (id) 0xD0 h: (subid) 0x06 d: skill id d: skill level
 * @author -Wooden-
 */
public class RequestExEnchantSkillInfo implements IClientIncomingPacket
{
	private int _skillId;
	private int _skillLevel;
	private int _skillSubLevel;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_skillId = packet.readD();
		_skillLevel = packet.readH();
		_skillSubLevel = packet.readH();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if ((_skillId <= 0) || (_skillLevel <= 0) || (_skillSubLevel < 0))
		{
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (!player.isInCategory(CategoryType.SIXTH_CLASS_GROUP))
		{
			return;
		}
		
		final Skill skill = SkillData.getInstance().getSkill(_skillId, _skillLevel, _skillSubLevel);
		if ((skill == null) || (skill.getId() != _skillId))
		{
			return;
		}
		final Set<Integer> route = EnchantSkillGroupsData.getInstance().getRouteForSkill(_skillId, _skillLevel);
		if (route.isEmpty())
		{
			return;
		}
		
		final Skill playerSkill = player.getKnownSkill(_skillId);
		if ((playerSkill.getLevel() != _skillLevel) || (playerSkill.getSubLevel() != _skillSubLevel))
		{
			return;
		}
		
		client.sendPacket(new ExEnchantSkillInfo(_skillId, _skillLevel, _skillSubLevel, playerSkill.getSubLevel()));
		// ExEnchantSkillInfoDetail - not really necessary I think
		// client.sendPacket(new ExEnchantSkillInfoDetail(SkillEnchantType.NORMAL, _skillId, _skillLevel, _skillSubLevel , activeChar));
	}
}