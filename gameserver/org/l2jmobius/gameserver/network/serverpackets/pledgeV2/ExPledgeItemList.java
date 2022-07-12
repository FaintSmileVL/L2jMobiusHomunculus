package org.l2jmobius.gameserver.network.serverpackets.pledgeV2;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.ClanShopData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ClanShopProductHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author Mobius
 */
public class ExPledgeItemList extends AbstractItemPacket
{
	final PlayerInstance _player;
	
	public ExPledgeItemList(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		if (_player.getClan() == null)
		{
			return false;
		}
		
		OutgoingPackets.EX_PLEDGE_ITEM_LIST.writeId(packet);
		
		packet.writeH(ClanShopData.getInstance().getProducts().size()); // Product count.
		for (ClanShopProductHolder product : ClanShopData.getInstance().getProducts())
		{
			writeItem(packet, product.getTradeItem());
			packet.writeC(_player.getClan().getLevel() < product.getClanLevel() ? 0 : 2); // 0 locked, 1 need activation, 2 available
			packet.writeQ(product.getAdena()); // Purchase price: adena
			packet.writeD(product.getFame()); // Purchase price: fame
			packet.writeC(product.getClanLevel()); // Required pledge level
			packet.writeC(0); // Required pledge mastery
			packet.writeQ(0); // Activation price: adena
			packet.writeD(0); // Activation price: reputation
			packet.writeD(0); // Time to deactivation
			packet.writeD(0); // Time to restock
			packet.writeH(0); // Current stock
			packet.writeH(0); // Total stock
		}
		
		return true;
	}
}
