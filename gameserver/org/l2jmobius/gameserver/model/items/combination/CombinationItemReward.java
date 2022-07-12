package org.l2jmobius.gameserver.model.items.combination;

import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author UnAfraid
 */
public class CombinationItemReward extends ItemHolder
{
	private final CombinationItemType _type;
	
	public CombinationItemReward(int id, int count, CombinationItemType type)
	{
		super(id, count);
		_type = type;
	}
	
	public CombinationItemType getType()
	{
		return _type;
	}
}
