package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExInzoneWaiting;

/**
 * @author Mobius
 */
public class RequestInzoneWaitingTime implements IClientIncomingPacket
{
	private boolean _hide;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_hide = packet.readC() == 0;
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
		client.sendPacket(new ExInzoneWaiting(player, _hide));
	}
}
