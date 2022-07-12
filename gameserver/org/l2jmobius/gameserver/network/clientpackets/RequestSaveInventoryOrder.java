package org.l2jmobius.gameserver.network.clientpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format:(ch) d[dd]
 * @author -Wooden-
 */
public class RequestSaveInventoryOrder implements IClientIncomingPacket
{
	private List<InventoryOrder> _order;
	
	/** client limit */
	private static final int LIMIT = 125;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		int sz = packet.readD();
		sz = Math.min(sz, LIMIT);
		_order = new ArrayList<>(sz);
		for (int i = 0; i < sz; i++)
		{
			final int objectId = packet.readD();
			final int order = packet.readD();
			_order.add(new InventoryOrder(objectId, order));
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player != null)
		{
			final Inventory inventory = player.getInventory();
			for (InventoryOrder order : _order)
			{
				final ItemInstance item = inventory.getItemByObjectId(order.objectID);
				if ((item != null) && (item.getItemLocation() == ItemLocation.INVENTORY))
				{
					item.setItemLocation(ItemLocation.INVENTORY, order.order);
				}
			}
		}
	}
	
	private static class InventoryOrder
	{
		int order;
		int objectID;
		
		public InventoryOrder(int id, int ord)
		{
			objectID = id;
			order = ord;
		}
	}
}
