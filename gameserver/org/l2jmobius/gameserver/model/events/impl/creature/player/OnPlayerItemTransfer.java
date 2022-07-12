package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.itemcontainer.ItemContainer;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnPlayerItemTransfer implements IBaseEvent
{
	private final PlayerInstance _player;
	private final ItemInstance _item;
	private final ItemContainer _container;
	
	public OnPlayerItemTransfer(PlayerInstance player, ItemInstance item, ItemContainer container)
	{
		_player = player;
		_item = item;
		_container = container;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public ItemContainer getContainer()
	{
		return _container;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_ITEM_TRANSFER;
	}
}
