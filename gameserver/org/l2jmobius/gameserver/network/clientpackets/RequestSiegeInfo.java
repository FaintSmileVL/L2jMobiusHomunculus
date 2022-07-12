package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: (c) (no data, trigger)
 * @author -Wooden-
 */
public class RequestSiegeInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return false;
	}
	
	@Override
	public void run(GameClient client)
	{
		// TODO this
	}
}
