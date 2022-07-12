package org.l2jmobius.gameserver.model.events.impl.item;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnItemBypassEvent implements IBaseEvent
{
	private final ItemInstance _item;
	private final PlayerInstance _player;
	private final String _event;
	
	public OnItemBypassEvent(ItemInstance item, PlayerInstance player, String event)
	{
		_item = item;
		_player = player;
		_event = event;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _player;
	}
	
	public String getEvent()
	{
		return _event;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ITEM_BYPASS_EVENT;
	}
}
