package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author ShanSoft Packets Structure: chdd
 */
public class RequestTeleportBookMark implements IClientIncomingPacket
{
	private int _id;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_id = packet.readD();
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
		player.teleportBookmarkGo(_id);
	}
}
