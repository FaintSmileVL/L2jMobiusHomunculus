package org.l2jmobius.gameserver.network.clientpackets.commission;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.CommissionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExCloseCommission;

/**
 * @author NosBit
 */
public class RequestCommissionBuyItem implements IClientIncomingPacket
{
	private long _commissionId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_commissionId = packet.readQ();
		// packet.readD(); // CommissionItemType
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
		
		CommissionManager.getInstance().buyItem(player, _commissionId);
	}
}
