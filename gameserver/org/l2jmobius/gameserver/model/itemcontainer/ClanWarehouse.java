package org.l2jmobius.gameserver.model.itemcontainer;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerClanWHItemAdd;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerClanWHItemDestroy;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerClanWHItemTransfer;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

public class ClanWarehouse extends Warehouse
{
	private final Clan _clan;
	
	public ClanWarehouse(Clan clan)
	{
		_clan = clan;
	}
	
	@Override
	public String getName()
	{
		return "ClanWarehouse";
	}
	
	@Override
	public int getOwnerId()
	{
		return _clan.getId();
	}
	
	@Override
	public PlayerInstance getOwner()
	{
		return _clan.getLeader().getPlayerInstance();
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.CLANWH;
	}
	
	@Override
	public boolean validateCapacity(long slots)
	{
		return (_items.size() + slots) <= Config.WAREHOUSE_SLOTS_CLAN;
	}
	
	@Override
	public ItemInstance addItem(String process, int itemId, long count, PlayerInstance actor, Object reference)
	{
		final ItemInstance item = super.addItem(process, itemId, count, actor, reference);
		
		// Notify to scripts
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerClanWHItemAdd(process, actor, item, this), item.getItem());
		return item;
	}
	
	@Override
	public ItemInstance addItem(String process, ItemInstance item, PlayerInstance actor, Object reference)
	{
		// Notify to scripts
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerClanWHItemAdd(process, actor, item, this), item.getItem());
		return super.addItem(process, item, actor, reference);
	}
	
	@Override
	public ItemInstance destroyItem(String process, ItemInstance item, long count, PlayerInstance actor, Object reference)
	{
		// Notify to scripts
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerClanWHItemDestroy(process, actor, item, count, this), item.getItem());
		return super.destroyItem(process, item, count, actor, reference);
	}
	
	@Override
	public ItemInstance transferItem(String process, int objectId, long count, ItemContainer target, PlayerInstance actor, Object reference)
	{
		final ItemInstance item = getItemByObjectId(objectId);
		
		// Notify to scripts
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerClanWHItemTransfer(process, actor, item, count, target), item.getItem());
		return super.transferItem(process, objectId, count, target, actor, reference);
	}
}
