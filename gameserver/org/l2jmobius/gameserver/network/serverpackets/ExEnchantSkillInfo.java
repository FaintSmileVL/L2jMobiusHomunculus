package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.EnchantSkillGroupsData;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExEnchantSkillInfo implements IClientOutgoingPacket
{
	private final Set<Integer> _routes;
	
	private final int _skillId;
	private final int _skillLevel;
	private final int _skillSubLevel;
	private final int _currentSubLevel;
	
	public ExEnchantSkillInfo(int skillId, int skillLevel, int skillSubLevel, int currentSubLevel)
	{
		_skillId = skillId;
		_skillLevel = skillLevel;
		_skillSubLevel = skillSubLevel;
		_currentSubLevel = currentSubLevel;
		_routes = EnchantSkillGroupsData.getInstance().getRouteForSkill(_skillId, _skillLevel);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_SKILL_INFO.writeId(packet);
		packet.writeD(_skillId);
		packet.writeH(_skillLevel);
		packet.writeH(_skillSubLevel);
		packet.writeD((_skillSubLevel % 1000) == EnchantSkillGroupsData.MAX_ENCHANT_LEVEL ? 0 : 1);
		packet.writeD(_skillSubLevel > 1000 ? 1 : 0);
		packet.writeD(_routes.size());
		_routes.forEach(route ->
		{
			final int routeId = route / 1000;
			final int currentRouteId = _skillSubLevel / 1000;
			final int subLevel = _currentSubLevel > 0 ? (route + (_currentSubLevel % 1000)) - 1 : route;
			packet.writeH(_skillLevel);
			packet.writeH(currentRouteId != routeId ? subLevel : Math.min(subLevel + 1, route + (EnchantSkillGroupsData.MAX_ENCHANT_LEVEL - 1)));
		});
		return true;
	}
}
