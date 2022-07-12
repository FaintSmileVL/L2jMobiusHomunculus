package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Mobius
 */
public class RequestStartShowKrateisCubeRank implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return false;
	}
	
	@Override
	public void run(GameClient client)
	{
		// TODO: Implement.
		System.out.println("RequestStartShowKrateisCubeRank");
	}
}
