package org.l2jmobius.gameserver.model.items;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author Zoey76
 */
public class PlayerItemTemplate extends ItemHolder
{
	private final boolean _equipped;
	
	/**
	 * @param set the set containing the values for this object
	 */
	public PlayerItemTemplate(StatSet set)
	{
		super(set.getInt("id"), set.getInt("count"));
		_equipped = set.getBoolean("equipped", false);
	}
	
	/**
	 * @return {@code true} if the items is equipped upon character creation, {@code false} otherwise
	 */
	public boolean isEquipped()
	{
		return _equipped;
	}
}