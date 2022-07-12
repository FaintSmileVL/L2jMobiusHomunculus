package org.l2jmobius.gameserver.network.clientpackets.primeshop;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.primeshop.ExBRGamePoint;

/**
 * @author Gnacik, UnAfraid
 */
public class RequestBRGamePoint implements IClientIncomingPacket
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
		if (player != null)
		{
			client.sendPacket(new ExBRGamePoint(player));
		}
	}
}