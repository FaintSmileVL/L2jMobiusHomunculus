package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.PledgeReceiveWarList;

/**
 * Format: (ch) dd
 * @author -Wooden-
 */
public class RequestPledgeWarList implements IClientIncomingPacket
{
	@SuppressWarnings("unused")
	private int _page;
	private int _tab;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_page = packet.readD();
		_tab = packet.readD();
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
		
		if (player.getClan() == null)
		{
			return;
		}
		
		player.sendPacket(new PledgeReceiveWarList(player.getClan(), _tab));
	}
}