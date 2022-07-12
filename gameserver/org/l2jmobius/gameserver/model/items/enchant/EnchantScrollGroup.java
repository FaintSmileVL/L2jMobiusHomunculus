package org.l2jmobius.gameserver.model.items.enchant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.l2jmobius.gameserver.model.items.Item;

/**
 * @author UnAfraid
 */
public class EnchantScrollGroup
{
	private final int _id;
	private List<EnchantRateItem> _rateGroups;
	
	public EnchantScrollGroup(int id)
	{
		_id = id;
	}
	
	/**
	 * @return id of current enchant scroll group.
	 */
	public int getId()
	{
		return _id;
	}
	
	/**
	 * Adds new rate group.
	 * @param group
	 */
	public void addRateGroup(EnchantRateItem group)
	{
		if (_rateGroups == null)
		{
			_rateGroups = new ArrayList<>();
		}
		_rateGroups.add(group);
	}
	
	/**
	 * @return {@code List} of all enchant rate items, Empty list if none.
	 */
	public List<EnchantRateItem> getRateGroups()
	{
		return _rateGroups != null ? _rateGroups : Collections.emptyList();
	}
	
	/**
	 * @param item
	 * @return {@link EnchantRateItem}, {@code NULL} in case non of rate items can be used with.
	 */
	public EnchantRateItem getRateGroup(Item item)
	{
		for (EnchantRateItem group : getRateGroups())
		{
			if (group.validate(item))
			{
				return group;
			}
		}
		return null;
	}
}
