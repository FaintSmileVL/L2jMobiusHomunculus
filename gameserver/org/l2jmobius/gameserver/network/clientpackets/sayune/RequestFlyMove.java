package org.l2jmobius.gameserver.network.clientpackets.sayune;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.SayuneRequest;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;

/**
 * @author UnAfraid
 */
public class RequestFlyMove implements IClientIncomingPacket
{
	private int _locationId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_locationId = packet.readD();
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
		
		final SayuneRequest request = player.getRequest(SayuneRequest.class);
		if (request == null)
		{
			return;
		}
		
		request.move(player, _locationId);
	}
}
