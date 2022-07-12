package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.data.xml.SkillTreeData;
import org.l2jmobius.gameserver.enums.AcquireSkillType;
import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.enums.Race;
import org.l2jmobius.gameserver.model.SkillLearn;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.AcquireSkillInfo;
import org.l2jmobius.gameserver.network.serverpackets.ExAcquireSkillInfo;

/**
 * Request Acquire Skill Info client packet implementation.
 * @author Zoey76
 */
public class RequestAcquireSkillInfo implements IClientIncomingPacket
{
	private int _id;
	private int _level;
	private AcquireSkillType _skillType;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_id = packet.readD();
		_level = packet.readD();
		_skillType = AcquireSkillType.getAcquireSkillType(packet.readD());
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if ((_id <= 0) || (_level <= 0))
		{
			LOGGER.warning(RequestAcquireSkillInfo.class.getSimpleName() + ": Invalid Id: " + _id + " or level: " + _level + "!");
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		final Npc trainer = player.getLastFolkNPC();
		if ((_skillType != AcquireSkillType.CLASS) && ((trainer == null) || !trainer.isNpc() || (!trainer.canInteract(player) && !player.isGM())))
		{
			return;
		}
		
		final Skill skill = SkillData.getInstance().getSkill(_id, _level);
		if (skill == null)
		{
			LOGGER.warning("Skill Id: " + _id + " level: " + _level + " is undefined. " + RequestAcquireSkillInfo.class.getName() + " failed.");
			return;
		}
		
		final SkillLearn s = SkillTreeData.getInstance().getSkillLearn(_skillType, _id, _level, player);
		if (s == null)
		{
			return;
		}
		
		switch (_skillType)
		{
			case TRANSFORM:
			case FISHING:
			case SUBCLASS:
			case COLLECT:
			case TRANSFER:
			case DUALCLASS:
			{
				client.sendPacket(new AcquireSkillInfo(_skillType, s));
				break;
			}
			case CLASS:
			{
				client.sendPacket(new ExAcquireSkillInfo(player, s));
				break;
			}
			case PLEDGE:
			{
				if (!player.isClanLeader())
				{
					return;
				}
				client.sendPacket(new AcquireSkillInfo(_skillType, s));
				break;
			}
			case SUBPLEDGE:
			{
				if (!player.isClanLeader() || !player.hasClanPrivilege(ClanPrivilege.CL_TROOPS_FAME))
				{
					return;
				}
				client.sendPacket(new AcquireSkillInfo(_skillType, s));
				break;
			}
			case ALCHEMY:
			{
				if (player.getRace() != Race.ERTHEIA)
				{
					return;
				}
				client.sendPacket(new AcquireSkillInfo(_skillType, s));
				break;
			}
			case REVELATION:
			{
				if ((player.getLevel() < 85) || !player.isInCategory(CategoryType.SIXTH_CLASS_GROUP))
				{
					return;
				}
				client.sendPacket(new AcquireSkillInfo(_skillType, s));
				break;
			}
			case REVELATION_DUALCLASS:
			{
				if (!player.isSubClassActive() || !player.isDualClassActive())
				{
					return;
				}
				client.sendPacket(new AcquireSkillInfo(_skillType, s));
				break;
			}
		}
	}
}
