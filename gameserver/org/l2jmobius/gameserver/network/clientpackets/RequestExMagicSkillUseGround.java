package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.ValidateLocation;
import org.l2jmobius.gameserver.util.Broadcast;
import org.l2jmobius.gameserver.util.Util;

/**
 * Fromat:(ch) dddddc
 * @author -Wooden-
 */
public class RequestExMagicSkillUseGround implements IClientIncomingPacket
{
	private int _x;
	private int _y;
	private int _z;
	private int _skillId;
	private boolean _ctrlPressed;
	private boolean _shiftPressed;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_x = packet.readD();
		_y = packet.readD();
		_z = packet.readD();
		_skillId = packet.readD();
		_ctrlPressed = packet.readD() != 0;
		_shiftPressed = packet.readC() != 0;
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
		final int level = player.getSkillLevel(_skillId);
		if (level <= 0)
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Get the Skill template corresponding to the skillID received from the client
		final Skill skill = SkillData.getInstance().getSkill(_skillId, level);
		
		// Check the validity of the skill
		if (skill != null)
		{
			player.setCurrentSkillWorldPosition(new Location(_x, _y, _z));
			
			// normally magicskilluse packet turns char client side but for these skills, it doesn't (even with correct target)
			player.setHeading(Util.calculateHeadingFrom(player.getX(), player.getY(), _x, _y));
			Broadcast.toKnownPlayers(player, new ValidateLocation(player));
			player.useMagic(skill, null, _ctrlPressed, _shiftPressed);
		}
		else
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			LOGGER.warning("No skill found with id " + _skillId + " and level " + level + " !!");
		}
	}
}
