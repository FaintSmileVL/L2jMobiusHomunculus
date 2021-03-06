package org.l2jmobius.gameserver.network.clientpackets;

import static org.l2jmobius.gameserver.model.actor.Npc.INTERACTION_DISTANCE;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.BuyListData;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.instance.MerchantInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.buylist.ProductList;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.ExBuySellList;
import org.l2jmobius.gameserver.network.serverpackets.ExUserInfoInvenWeight;
import org.l2jmobius.gameserver.util.Util;

/**
 * RequestRefundItem client packet class.
 */
public class RequestRefundItem implements IClientIncomingPacket
{
	private static final int BATCH_LENGTH = 4; // length of the one item
	private static final int CUSTOM_CB_SELL_LIST = 423;
	
	private int _listId;
	private int[] _items = null;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_listId = packet.readD();
		final int count = packet.readD();
		if ((count <= 0) || (count > Config.MAX_ITEM_IN_PACKET) || ((count * BATCH_LENGTH) != packet.getReadableBytes()))
		{
			return false;
		}
		
		_items = new int[count];
		for (int i = 0; i < count; i++)
		{
			_items[i] = packet.readD();
		}
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
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("refund"))
		{
			player.sendMessage("You are using refund too fast.");
			return;
		}
		
		if ((_items == null) || !player.hasRefund())
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final WorldObject target = player.getTarget();
		MerchantInstance merchant = null;
		if (!player.isGM() && (_listId != CUSTOM_CB_SELL_LIST))
		{
			if (!(target instanceof MerchantInstance) || !player.isInsideRadius3D(target, INTERACTION_DISTANCE) || (player.getInstanceId() != target.getInstanceId()))
			{
				client.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			merchant = (MerchantInstance) target;
		}
		
		if ((merchant == null) && !player.isGM() && (_listId != CUSTOM_CB_SELL_LIST))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final ProductList buyList = BuyListData.getInstance().getBuyList(_listId);
		if (buyList == null)
		{
			Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent a false BuyList list_id " + _listId, Config.DEFAULT_PUNISH);
			return;
		}
		
		if ((merchant != null) && !buyList.isNpcAllowed(merchant.getId()))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		long weight = 0;
		long adena = 0;
		long slots = 0;
		
		final ItemInstance[] refund = player.getRefund().getItems().toArray(new ItemInstance[0]);
		final int[] objectIds = new int[_items.length];
		for (int i = 0; i < _items.length; i++)
		{
			final int idx = _items[i];
			if ((idx < 0) || (idx >= refund.length))
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent invalid refund index", Config.DEFAULT_PUNISH);
				return;
			}
			
			// check for duplicates - indexes
			for (int j = i + 1; j < _items.length; j++)
			{
				if (idx == _items[j])
				{
					Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent duplicate refund index", Config.DEFAULT_PUNISH);
					return;
				}
			}
			
			final ItemInstance item = refund[idx];
			final Item template = item.getItem();
			objectIds[i] = item.getObjectId();
			
			// second check for duplicates - object ids
			for (int j = 0; j < i; j++)
			{
				if (objectIds[i] == objectIds[j])
				{
					Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " has duplicate items in refund list", Config.DEFAULT_PUNISH);
					return;
				}
			}
			
			final long count = item.getCount();
			weight += count * template.getWeight();
			adena += count * (template.getReferencePrice() / 2);
			if (!template.isStackable())
			{
				slots += count;
			}
			else if (player.getInventory().getItemByItemId(template.getId()) == null)
			{
				slots++;
			}
		}
		
		if ((weight > Integer.MAX_VALUE) || (weight < 0) || !player.getInventory().validateWeight((int) weight))
		{
			client.sendPacket(SystemMessageId.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if ((slots > Integer.MAX_VALUE) || (slots < 0) || !player.getInventory().validateCapacity((int) slots))
		{
			client.sendPacket(SystemMessageId.YOUR_INVENTORY_IS_FULL);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!Config.MERCHANT_ZERO_SELL_PRICE && ((adena < 0) || !player.reduceAdena("Refund", adena, player.getLastFolkNPC(), false)))
		{
			client.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		for (int i = 0; i < _items.length; i++)
		{
			final ItemInstance item = player.getRefund().transferItem("Refund", objectIds[i], Long.MAX_VALUE, player.getInventory(), player, player.getLastFolkNPC());
			if (item == null)
			{
				LOGGER.warning("Error refunding object for char " + player.getName() + " (newitem == null)");
			}
		}
		
		// Update current load status on player
		client.sendPacket(new ExUserInfoInvenWeight(player));
		client.sendPacket(new ExBuySellList(player, true));
	}
}
