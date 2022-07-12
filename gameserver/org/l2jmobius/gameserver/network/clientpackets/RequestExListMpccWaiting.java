package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExListMpccWaiting;

/**
 * @author Sdw
 */
public class RequestExListMpccWaiting implements IClientIncomingPacket
{
	private int _page;
	private int _location;
	private int _level;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_page = packet.readD();
		_location = packet.readD();
		_level = packet.readD();
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
		
		player.sendPacket(new ExListMpccWaiting(_page, _location, _level));
	}
}
