package org.l2jmobius.gameserver.network.clientpackets.luckygame;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;

/**
 * @author Sdw
 */
public class RequestLuckyGameStartInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
	}
}
