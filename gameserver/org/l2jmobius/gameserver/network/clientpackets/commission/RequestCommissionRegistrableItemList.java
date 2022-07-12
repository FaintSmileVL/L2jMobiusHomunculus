package org.l2jmobius.gameserver.network.clientpackets.commission;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.CommissionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExCloseCommission;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExResponseCommissionItemList;

/**
 * @author NosBit
 */
public class RequestCommissionRegistrableItemList implements IClientIncomingPacket
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
		if (player == null)
		{
			return;
		}
		
		if (!CommissionManager.isPlayerAllowedToInteract(player))
		{
			client.sendPacket(ExCloseCommission.STATIC_PACKET);
			return;
		}
		
		client.sendPacket(new ExResponseCommissionItemList(1, player.getInventory().getAvailableItems(false, false, false)));
		client.sendPacket(new ExResponseCommissionItemList(2, player.getInventory().getAvailableItems(false, false, false)));
	}
}
