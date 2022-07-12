package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author ShanSoft Packets Structure: chddd
 */
public class RequestChangeBookMarkSlot implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return false;
	}
	
	@Override
	public void run(GameClient client)
	{
	}
}
