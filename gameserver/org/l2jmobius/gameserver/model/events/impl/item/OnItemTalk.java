package org.l2jmobius.gameserver.model.events.impl.item;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnItemTalk implements IBaseEvent
{
	private final ItemInstance _item;
	private final PlayerInstance _player;
	
	public OnItemTalk(ItemInstance item, PlayerInstance player)
	{
		_item = item;
		_player = player;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _player;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ITEM_TALK;
	}
}
