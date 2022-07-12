package org.l2jmobius.gameserver.network.clientpackets.raidbossinfo;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;

/**
 * @author Mobius
 */
public class RequestRaidServerInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		// System.out.println("RequestRaidServerInfo");
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
	}
}
