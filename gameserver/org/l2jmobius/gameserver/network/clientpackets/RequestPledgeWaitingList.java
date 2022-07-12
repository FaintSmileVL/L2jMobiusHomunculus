package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExPledgeWaitingList;

/**
 * @author Sdw
 */
public class RequestPledgeWaitingList implements IClientIncomingPacket
{
	private int _clanId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_clanId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getClanId() != _clanId))
		{
			return;
		}
		
		client.sendPacket(new ExPledgeWaitingList(_clanId));
	}
}
