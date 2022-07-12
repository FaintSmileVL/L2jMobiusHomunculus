package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.EnchantItemRequest;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExRemoveEnchantSupportItemResult;

/**
 * @author Sdw
 */
public class RequestExRemoveEnchantSupportItem implements IClientIncomingPacket
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
		
		final EnchantItemRequest request = player.getRequest(EnchantItemRequest.class);
		if ((request == null) || request.isProcessing())
		{
			return;
		}
		
		final ItemInstance supportItem = request.getSupportItem();
		if ((supportItem == null) || (supportItem.getCount() < 1))
		{
			request.setSupportItem(PlayerInstance.ID_NONE);
		}
		
		request.setTimestamp(Chronos.currentTimeMillis());
		player.sendPacket(ExRemoveEnchantSupportItemResult.STATIC_PACKET);
	}
}
