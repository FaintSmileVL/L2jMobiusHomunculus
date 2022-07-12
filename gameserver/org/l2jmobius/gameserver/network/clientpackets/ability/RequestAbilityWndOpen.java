package org.l2jmobius.gameserver.network.clientpackets.ability;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.ability.ExAcquireAPSkillList;
import org.l2jmobius.gameserver.network.serverpackets.ability.ExShowAPListWnd;

/**
 * @author Sdw
 */
public class RequestAbilityWndOpen implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (player.getLevel() < 85)
		{
			player.sendPacket(SystemMessageId.REACH_LEVEL_85_TO_USE_THE_ABILITY);
			return;
		}
		
		player.sendPacket(ExShowAPListWnd.STATIC_PACKET);
		player.sendPacket(new ExAcquireAPSkillList(player));
	}
}
