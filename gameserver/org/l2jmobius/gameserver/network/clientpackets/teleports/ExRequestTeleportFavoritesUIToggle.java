package org.l2jmobius.gameserver.network.clientpackets.teleports;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.teleports.ExTeleportFavoritesList;

/**
 * @author Mobius
 */
public class ExRequestTeleportFavoritesUIToggle implements IClientIncomingPacket
{
	private boolean _enable;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_enable = packet.readC() == 1;
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
		
		client.sendPacket(new ExTeleportFavoritesList(player, _enable));
	}
}
