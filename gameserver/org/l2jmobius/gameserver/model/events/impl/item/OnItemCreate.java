package org.l2jmobius.gameserver.model.events.impl.item;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnItemCreate implements IBaseEvent
{
	private final String _process;
	private final ItemInstance _item;
	private final Creature _creature;
	private final Object _reference;
	
	public OnItemCreate(String process, ItemInstance item, Creature actor, Object reference)
	{
		_process = process;
		_item = item;
		_creature = actor;
		_reference = reference;
	}
	
	public String getProcess()
	{
		return _process;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public Creature getActiveChar()
	{
		return _creature;
	}
	
	public Object getReference()
	{
		return _reference;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ITEM_CREATE;
	}
}
