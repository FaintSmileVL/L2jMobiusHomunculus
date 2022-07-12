package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.handler.BypassHandler;
import org.l2jmobius.gameserver.handler.IBypassHandler;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * format ch c: (id) 0xD0 h: (subid) 0x13
 * @author -Wooden-
 */
public class RequestOlympiadMatchList implements IClientIncomingPacket
{
	private static final String COMMAND = "arenalist";
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || !player.inObserverMode())
		{
			return;
		}
		
		final IBypassHandler handler = BypassHandler.getInstance().getHandler(COMMAND);
		if (handler != null)
		{
			handler.useBypass(COMMAND, player, null);
		}
	}
}