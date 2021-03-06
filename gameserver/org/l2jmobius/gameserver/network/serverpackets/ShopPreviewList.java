package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.buylist.Product;
import org.l2jmobius.gameserver.model.buylist.ProductList;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShopPreviewList implements IClientOutgoingPacket
{
	private final int _listId;
	private final Collection<Product> _list;
	private final long _money;
	
	public ShopPreviewList(ProductList list, long currentMoney)
	{
		_listId = list.getListId();
		_list = list.getProducts();
		_money = currentMoney;
	}
	
	public ShopPreviewList(Collection<Product> lst, int listId, long currentMoney)
	{
		_listId = listId;
		_list = lst;
		_money = currentMoney;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOP_PREVIEW_LIST.writeId(packet);
		
		packet.writeD(5056);
		packet.writeQ(_money); // current money
		packet.writeD(_listId);
		
		int newlength = 0;
		for (Product product : _list)
		{
			if (product.getItem().isEquipable())
			{
				newlength++;
			}
		}
		packet.writeH(newlength);
		
		for (Product product : _list)
		{
			if (product.getItem().isEquipable())
			{
				packet.writeD(product.getItemId());
				packet.writeH(product.getItem().getType2()); // item type2
				
				if (product.getItem().getType1() != Item.TYPE1_ITEM_QUESTITEM_ADENA)
				{
					packet.writeQ(product.getItem().getBodyPart()); // rev 415 slot 0006-lr.ear 0008-neck 0030-lr.finger 0040-head 0080-?? 0100-l.hand 0200-gloves 0400-chest 0800-pants 1000-feet 2000-?? 4000-r.hand 8000-r.hand
				}
				else
				{
					packet.writeQ(0x00); // rev 415 slot 0006-lr.ear 0008-neck 0030-lr.finger 0040-head 0080-?? 0100-l.hand 0200-gloves 0400-chest 0800-pants 1000-feet 2000-?? 4000-r.hand 8000-r.hand
				}
				
				packet.writeQ(Config.WEAR_PRICE);
			}
		}
		return true;
	}
}
