package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: (ch) d
 * @author -Wooden-
 */
public class RequestGetBossRecord implements IClientIncomingPacket
{
	private int _bossId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_bossId = packet.readD();
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
		
		LOGGER.warning("Player " + player + " (boss ID: " + _bossId + ") used unsuded packet " + RequestGetBossRecord.class.getSimpleName());
	}
}