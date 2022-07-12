package org.l2jmobius.gameserver.model.items.combination;

import java.util.EnumMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.StatSet;

/**
 * @author UnAfraid
 */
public class CombinationItem
{
	private final int _itemOne;
	private final int _itemTwo;
	private final int _chance;
	private final Map<CombinationItemType, CombinationItemReward> _rewards = new EnumMap<>(CombinationItemType.class);
	
	public CombinationItem(StatSet set)
	{
		_itemOne = set.getInt("one");
		_itemTwo = set.getInt("two");
		_chance = set.getInt("chance");
	}
	
	public int getItemOne()
	{
		return _itemOne;
	}
	
	public int getItemTwo()
	{
		return _itemTwo;
	}
	
	public int getChance()
	{
		return _chance;
	}
	
	public void addReward(CombinationItemReward item)
	{
		_rewards.put(item.getType(), item);
	}
	
	public CombinationItemReward getReward(CombinationItemType type)
	{
		return _rewards.get(type);
	}
}
