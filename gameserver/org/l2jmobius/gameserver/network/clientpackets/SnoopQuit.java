package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author -Wooden-
 */
public class SnoopQuit implements IClientIncomingPacket
{
	private int _snoopID;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_snoopID = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance target = World.getInstance().getPlayer(_snoopID);
		if (target == null)
		{
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		target.removeSnooper(player);
		player.removeSnooped(target);
		
	}
}
