package org.l2jmobius.gameserver.network.clientpackets.pledgeV2;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.ClanShopData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ClanShopProductHolder;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.pledgeV2.ExPledgeItemBuy;

/**
 * @author Mobius
 */
public class RequestExPledgeItemBuy implements IClientIncomingPacket
{
	private int _itemId;
	private int _count;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_itemId = packet.readD();
		_count = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getClan() == null))
		{
			client.sendPacket(new ExPledgeItemBuy(1));
			return;
		}
		
		final ClanShopProductHolder product = ClanShopData.getInstance().getProduct(_itemId);
		if (product == null)
		{
			client.sendPacket(new ExPledgeItemBuy(1));
			return;
		}
		
		if (player.getClan().getLevel() < product.getClanLevel())
		{
			client.sendPacket(new ExPledgeItemBuy(2));
			return;
		}
		
		final long slots = product.getTradeItem().getItem().isStackable() ? 1 : product.getTradeItem().getCount() * _count;
		final long weight = product.getTradeItem().getItem().getWeight() * product.getTradeItem().getCount() * _count;
		if (!player.getInventory().validateWeight(weight) || !player.getInventory().validateCapacity(slots))
		{
			client.sendPacket(new ExPledgeItemBuy(3));
			return;
		}
		
		if ((player.getAdena() < (product.getAdena() * _count)) || (player.getFame() < (product.getFame() * _count)))
		{
			client.sendPacket(new ExPledgeItemBuy(3));
			return;
		}
		
		if (product.getAdena() > 0)
		{
			player.reduceAdena("ClanShop", product.getAdena() * _count, player, true);
		}
		if (product.getFame() > 0)
		{
			player.setFame(player.getFame() - (product.getFame() * _count));
		}
		
		player.addItem("ClanShop", _itemId, product.getCount() * _count, player, true);
		client.sendPacket(new ExPledgeItemBuy(0));
	}
}