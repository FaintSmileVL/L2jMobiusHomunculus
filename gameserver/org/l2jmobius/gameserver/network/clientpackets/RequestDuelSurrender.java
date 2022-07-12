package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.DuelManager;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format:(ch) just a trigger
 * @author -Wooden-
 */
public class RequestDuelSurrender implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		DuelManager.getInstance().doSurrender(client.getPlayer());
	}
}
