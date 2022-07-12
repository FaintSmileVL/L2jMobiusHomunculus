package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.AbnormalType;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author KenM
 */
public class RequestDispel implements IClientIncomingPacket
{
	private int _objectId;
	private int _skillId;
	private int _skillLevel;
	private int _skillSubLevel;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		_skillId = packet.readD();
		_skillLevel = packet.readH();
		_skillSubLevel = packet.readH();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if ((_skillId <= 0) || (_skillLevel <= 0))
		{
			return;
		}
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		final Skill skill = SkillData.getInstance().getSkill(_skillId, _skillLevel, _skillSubLevel);
		if (skill == null)
		{
			return;
		}
		if (!skill.canBeDispelled() || skill.isStayAfterDeath() || skill.isDebuff())
		{
			return;
		}
		if (skill.getAbnormalType() == AbnormalType.TRANSFORM)
		{
			return;
		}
		if (skill.isDance() && !Config.DANCE_CANCEL_BUFF)
		{
			return;
		}
		if (player.getObjectId() == _objectId)
		{
			player.stopSkillEffects(true, _skillId);
		}
		else
		{
			final Summon pet = player.getPet();
			if ((pet != null) && (pet.getObjectId() == _objectId))
			{
				pet.stopSkillEffects(true, _skillId);
			}
			
			final Summon servitor = player.getServitor(_objectId);
			if (servitor != null)
			{
				servitor.stopSkillEffects(true, _skillId);
			}
		}
	}
}
