package org.l2jmobius.gameserver.network.clientpackets.pledgeV2;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.pledgeV2.ExPledgeMasteryInfo;

/**
 * @author Mobius
 */
public class RequestExPledgeMasteryInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getClan() == null))
		{
			return;
		}
		
		client.sendPacket(new ExPledgeMasteryInfo(player));
	}
}