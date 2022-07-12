package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.itemcontainer.ItemContainer;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnPlayerClanWHItemDestroy implements IBaseEvent
{
	private final String _process;
	private final PlayerInstance _player;
	private final ItemInstance _item;
	private final long _count;
	private final ItemContainer _container;
	
	public OnPlayerClanWHItemDestroy(String process, PlayerInstance player, ItemInstance item, long count, ItemContainer container)
	{
		_process = process;
		_player = player;
		_item = item;
		_count = count;
		_container = container;
	}
	
	public String getProcess()
	{
		return _process;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public long getCount()
	{
		return _count;
	}
	
	public ItemContainer getContainer()
	{
		return _container;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_CLAN_WH_ITEM_DESTROY;
	}
}
