package org.l2jmobius.gameserver.model;

import java.util.List;

import org.l2jmobius.gameserver.model.holders.RestorationItemHolder;

/**
 * @author Zoey76
 */
public class ExtractableProductItem
{
	private final List<RestorationItemHolder> _items;
	private final double _chance;
	
	public ExtractableProductItem(List<RestorationItemHolder> items, double chance)
	{
		_items = items;
		_chance = chance;
	}
	
	/**
	 * @return the the production list.
	 */
	public List<RestorationItemHolder> getItems()
	{
		return _items;
	}
	
	/**
	 * @return the chance of the production list.
	 */
	public double getChance()
	{
		return _chance;
	}
}
