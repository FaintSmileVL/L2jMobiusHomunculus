package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.ItemAuctionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.itemauction.ItemAuction;
import org.l2jmobius.gameserver.model.itemauction.ItemAuctionInstance;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Forsaiken
 */
public class RequestBidItemAuction implements IClientIncomingPacket
{
	private int _instanceId;
	private long _bid;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_instanceId = packet.readD();
		_bid = packet.readQ();
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
		
		// can't use auction fp here
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("auction"))
		{
			player.sendMessage("You are bidding too fast.");
			return;
		}
		
		if ((_bid < 0) || (_bid > Inventory.MAX_ADENA))
		{
			return;
		}
		
		final ItemAuctionInstance instance = ItemAuctionManager.getInstance().getManagerInstance(_instanceId);
		if (instance != null)
		{
			final ItemAuction auction = instance.getCurrentAuction();
			if (auction != null)
			{
				auction.registerBid(player, _bid);
			}
		}
	}
}