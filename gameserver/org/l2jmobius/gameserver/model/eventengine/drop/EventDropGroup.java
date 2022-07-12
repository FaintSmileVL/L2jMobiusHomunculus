package org.l2jmobius.gameserver.model.eventengine.drop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author UnAfraid
 */
public class EventDropGroup
{
	private final List<EventDropItem> _items = new ArrayList<>();
	private final double _chance;
	
	public EventDropGroup(double chance)
	{
		_chance = chance;
	}
	
	public double getChance()
	{
		return _chance;
	}
	
	public List<EventDropItem> getItems()
	{
		return _items;
	}
	
	public void addItem(EventDropItem item)
	{
		_items.add(item);
	}
}
