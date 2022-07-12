package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author Mobius
 */
public class OnPlayerItemUnequip implements IBaseEvent
{
	private final PlayerInstance _player;
	private final ItemInstance _item;
	
	public OnPlayerItemUnequip(PlayerInstance player, ItemInstance item)
	{
		_player = player;
		_item = item;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_ITEM_UNEQUIP;
	}
}
