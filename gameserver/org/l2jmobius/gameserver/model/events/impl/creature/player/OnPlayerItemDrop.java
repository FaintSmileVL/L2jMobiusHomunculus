package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnPlayerItemDrop implements IBaseEvent
{
	private final PlayerInstance _player;
	private final ItemInstance _item;
	private final Location _loc;
	
	public OnPlayerItemDrop(PlayerInstance player, ItemInstance item, Location loc)
	{
		_player = player;
		_item = item;
		_loc = loc;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public Location getLocation()
	{
		return _loc;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_ITEM_DROP;
	}
}
