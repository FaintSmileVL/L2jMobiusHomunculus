package org.l2jmobius.gameserver.model.holders;

import java.util.Collections;
import java.util.List;

/**
 * @author UnAfraid
 */
public class CrystallizationDataHolder
{
	private final int _id;
	private final List<ItemChanceHolder> _items;
	
	public CrystallizationDataHolder(int id, List<ItemChanceHolder> items)
	{
		_id = id;
		_items = Collections.unmodifiableList(items);
	}
	
	public int getId()
	{
		return _id;
	}
	
	public List<ItemChanceHolder> getItems()
	{
		return Collections.unmodifiableList(_items);
	}
}
