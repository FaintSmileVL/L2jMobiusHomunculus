package org.l2jmobius.gameserver.network.clientpackets;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.BuyListData;
import org.l2jmobius.gameserver.enums.Race;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.MerchantInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.buylist.Product;
import org.l2jmobius.gameserver.model.buylist.ProductList;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.items.Armor;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.Weapon;
import org.l2jmobius.gameserver.model.items.type.ArmorType;
import org.l2jmobius.gameserver.model.items.type.WeaponType;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.ExUserInfoEquipSlot;
import org.l2jmobius.gameserver.network.serverpackets.ShopPreviewInfo;
import org.l2jmobius.gameserver.util.Util;

/**
 ** @author Gnacik
 */
public class RequestPreviewItem implements IClientIncomingPacket
{
	@SuppressWarnings("unused")
	private int _unk;
	private int _listId;
	private int _count;
	private int[] _items;
	
	private class RemoveWearItemsTask implements Runnable
	{
		private final PlayerInstance _player;
		
		protected RemoveWearItemsTask(PlayerInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			try
			{
				_player.sendPacket(SystemMessageId.YOU_ARE_NO_LONGER_TRYING_ON_EQUIPMENT_2);
				_player.sendPacket(new ExUserInfoEquipSlot(_player));
			}
			catch (Exception e)
			{
				LOGGER.log(Level.SEVERE, "", e);
			}
		}
	}
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_unk = packet.readD();
		_listId = packet.readD();
		_count = packet.readD();
		if (_count < 0)
		{
			_count = 0;
		}
		if (_count > 100)
		{
			return false; // prevent too long lists
		}
		
		// Create _items table that will contain all ItemID to Wear
		_items = new int[_count];
		
		// Fill _items table with all ItemID to Wear
		for (int i = 0; i < _count; i++)
		{
			_items[i] = packet.readD();
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (_items == null)
		{
			return;
		}
		
		// Get the current player and return if null
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("buy"))
		{
			player.sendMessage("You are buying too fast.");
			return;
		}
		
		// If Alternate rule Karma punishment is set to true, forbid Wear to player with Karma
		if (!Config.ALT_GAME_KARMA_PLAYER_CAN_SHOP && (player.getReputation() < 0))
		{
			return;
		}
		
		// Check current target of the player and the INTERACTION_DISTANCE
		final WorldObject target = player.getTarget();
		if (!player.isGM() && ((target == null) // No target (i.e. GM Shop)
			|| !(target instanceof MerchantInstance) // Target not a merchant
			|| !player.isInsideRadius2D(target, Npc.INTERACTION_DISTANCE) // Distance is too far
		))
		{
			return;
		}
		
		if ((_count < 1) || (_listId >= 4000000))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Get the current merchant targeted by the player
		final MerchantInstance merchant = (target instanceof MerchantInstance) ? (MerchantInstance) target : null;
		if (merchant == null)
		{
			LOGGER.warning("Null merchant!");
			return;
		}
		
		final ProductList buyList = BuyListData.getInstance().getBuyList(_listId);
		if (buyList == null)
		{
			Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent a false BuyList list_id " + _listId, Config.DEFAULT_PUNISH);
			return;
		}
		
		long totalPrice = 0;
		final Map<Integer, Integer> itemList = new HashMap<>();
		for (int i = 0; i < _count; i++)
		{
			final int itemId = _items[i];
			final Product product = buyList.getProductByItemId(itemId);
			if (product == null)
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent a false BuyList list_id " + _listId + " and item_id " + itemId, Config.DEFAULT_PUNISH);
				return;
			}
			
			final Item template = product.getItem();
			if (template == null)
			{
				continue;
			}
			
			final int slot = Inventory.getPaperdollIndex(template.getBodyPart());
			if (slot < 0)
			{
				continue;
			}
			
			if (template instanceof Weapon)
			{
				if (player.getRace() == Race.KAMAEL)
				{
					if (template.getItemType() == WeaponType.NONE)
					{
						continue;
					}
					else if ((template.getItemType() == WeaponType.RAPIER) || (template.getItemType() == WeaponType.CROSSBOW) || (template.getItemType() == WeaponType.ANCIENTSWORD))
					{
						continue;
					}
				}
			}
			else if (template instanceof Armor)
			{
				if ((player.getRace() == Race.KAMAEL) && ((template.getItemType() == ArmorType.HEAVY) || (template.getItemType() == ArmorType.MAGIC)))
				{
					continue;
				}
			}
			
			if (itemList.containsKey(slot))
			{
				player.sendPacket(SystemMessageId.YOU_CAN_NOT_TRY_THOSE_ITEMS_ON_AT_THE_SAME_TIME);
				return;
			}
			
			itemList.put(slot, itemId);
			totalPrice += Config.WEAR_PRICE;
			if (totalPrice > Inventory.MAX_ADENA)
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " tried to purchase over " + Inventory.MAX_ADENA + " adena worth of goods.", Config.DEFAULT_PUNISH);
				return;
			}
		}
		
		// Charge buyer and add tax to castle treasury if not owned by npc clan because a Try On is not Free
		if ((totalPrice < 0) || !player.reduceAdena("Wear", totalPrice, player.getLastFolkNPC(), true))
		{
			player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			return;
		}
		
		if (!itemList.isEmpty())
		{
			player.sendPacket(new ShopPreviewInfo(itemList));
			// Schedule task
			ThreadPool.schedule(new RemoveWearItemsTask(player), Config.WEAR_DELAY * 1000);
		}
	}
}
