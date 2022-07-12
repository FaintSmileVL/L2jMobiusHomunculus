package org.l2jmobius.gameserver.model.eventengine.drop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.Rnd;
import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author UnAfraid
 */
public class NormalDrop implements IEventDrop
{
	private final List<EventDropItem> _items = new ArrayList<>();
	
	public List<EventDropItem> getItems()
	{
		return _items;
	}
	
	public void addItem(EventDropItem item)
	{
		_items.add(item);
	}
	
	@Override
	public Collection<ItemHolder> calculateDrops()
	{
		final List<ItemHolder> rewards = new ArrayList<>();
		double totalChance = 0;
		final double random = (Rnd.nextDouble() * 100);
		for (EventDropItem item : _items)
		{
			totalChance += item.getChance();
			if (totalChance > random)
			{
				final long count = Rnd.get(item.getMin(), item.getMax());
				if (count > 0)
				{
					rewards.add(new ItemHolder(item.getId(), count));
				}
			}
		}
		return rewards;
	}
}
