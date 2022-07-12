package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.CommonSkill;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

public class RequestMagicSkillUse implements IClientIncomingPacket
{
	private int _magicId;
	private boolean _ctrlPressed;
	private boolean _shiftPressed;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_magicId = packet.readD(); // Identifier of the used skill
		_ctrlPressed = packet.readD() != 0; // True if it's a ForceAttack : Ctrl pressed
		_shiftPressed = packet.readC() != 0; // True if Shift pressed
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		// Get the current PlayerInstance of the player
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		// Get the level of the used skill
		Skill skill = player.getKnownSkill(_magicId);
		if (skill == null)
		{
			if ((_magicId == CommonSkill.HAIR_ACCESSORY_SET.getId()) //
				|| ((_magicId > 1565) && (_magicId < 1570))) // subClass change SkillTree
			{
				skill = SkillData.getInstance().getSkill(_magicId, 1);
			}
			else
			{
				player.sendPacket(ActionFailed.STATIC_PACKET);
				// if (_magicId > 0)
				// {
				// LOGGER.warning("Skill Id " + _magicId + " not found in player: " + player);
				// }
				return;
			}
		}
		
		// Skill is blocked from player use.
		if (skill.isBlockActionUseSkill())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Avoid Use of Skills in AirShip.
		if (player.isInAirShip())
		{
			player.sendPacket(SystemMessageId.THIS_ACTION_IS_PROHIBITED_WHILE_MOUNTED_OR_ON_AN_AIRSHIP);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.onActionRequest();
		
		player.useMagic(skill, null, _ctrlPressed, _shiftPressed);
	}
}
