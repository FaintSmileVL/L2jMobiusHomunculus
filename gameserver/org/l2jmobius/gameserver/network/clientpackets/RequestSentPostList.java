package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExShowSentPostList;

/**
 * @author Migi, DS
 */
public class RequestSentPostList implements IClientIncomingPacket
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
		if ((player == null) || !Config.ALLOW_MAIL)
		{
			return;
		}
		
		// if (!activeChar.isInsideZone(ZoneId.PEACE))
		// {
		// player.sendPacket(SystemMessageId.YOU_CANNOT_RECEIVE_OR_SEND_MAIL_WITH_ATTACHED_ITEMS_IN_NON_PEACE_ZONE_REGIONS);
		// return;
		// }
		client.sendPacket(new ExShowSentPostList(player.getObjectId()));
	}
}
