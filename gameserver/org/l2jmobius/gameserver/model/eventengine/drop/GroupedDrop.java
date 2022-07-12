package org.l2jmobius.gameserver.model.eventengine.drop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.Rnd;
import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author UnAfraid
 */
public class GroupedDrop implements IEventDrop
{
	private final List<EventDropGroup> _groups = new ArrayList<>();
	
	public List<EventDropGroup> getGroups()
	{
		return _groups;
	}
	
	public void addGroup(EventDropGroup group)
	{
		_groups.add(group);
	}
	
	@Override
	public Collection<ItemHolder> calculateDrops()
	{
		final List<ItemHolder> rewards = new ArrayList<>();
		for (EventDropGroup group : _groups)
		{
			if ((Rnd.nextDouble() * 100) < group.getChance())
			{
				double totalChance = 0;
				final double random = (Rnd.nextDouble() * 100);
				for (EventDropItem item : group.getItems())
				{
					totalChance += item.getChance();
					if (totalChance > random)
					{
						final long count = Rnd.get(item.getMin(), item.getMax());
						if (count > 0)
						{
							rewards.add(new ItemHolder(item.getId(), count));
							break;
						}
					}
				}
			}
		}
		return rewards;
	}
}
