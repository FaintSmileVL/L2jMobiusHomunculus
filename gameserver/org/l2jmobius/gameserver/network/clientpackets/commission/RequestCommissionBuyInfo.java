package org.l2jmobius.gameserver.network.clientpackets.commission;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.CommissionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.commission.CommissionItem;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExCloseCommission;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExResponseCommissionBuyInfo;

/**
 * @author NosBit
 */
public class RequestCommissionBuyInfo implements IClientIncomingPacket
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
		
		if (!player.isInventoryUnder80(false) || (player.getWeightPenalty() >= 3))
		{
			client.sendPacket(SystemMessageId.IF_THE_WEIGHT_IS_80_OR_MORE_AND_THE_INVENTORY_AMOUNT_IS_90_OR_MORE_PURCHASE_CANCELLATION_IS_NOT_POSSIBLE);
			client.sendPacket(ExResponseCommissionBuyInfo.FAILED);
			return;
		}
		
		final CommissionItem commissionItem = CommissionManager.getInstance().getCommissionItem(_commissionId);
		if (commissionItem != null)
		{
			client.sendPacket(new ExResponseCommissionBuyInfo(commissionItem));
		}
		else
		{
			client.sendPacket(SystemMessageId.ITEM_PURCHASE_IS_NOT_AVAILABLE_BECAUSE_THE_CORRESPONDING_ITEM_DOES_NOT_EXIST);
			client.sendPacket(ExResponseCommissionBuyInfo.FAILED);
		}
	}
}
