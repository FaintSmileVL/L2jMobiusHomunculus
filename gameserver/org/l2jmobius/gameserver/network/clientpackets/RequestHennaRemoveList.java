package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.HennaRemoveList;

public class RequestHennaRemoveList implements IClientIncomingPacket
{
	@SuppressWarnings("unused")
	private int _unknown;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_unknown = packet.readD(); // TODO: Identify.
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
		client.sendPacket(new HennaRemoveList(player));
	}
}
