package org.l2jmobius.gameserver.model.holders;

/**
 * @author Mobius
 */
public class RestorationItemHolder
{
	private final int _id;
	private final long _count;
	private final int _minEnchant;
	private final int _maxEnchant;
	
	public RestorationItemHolder(int id, long count, int minEnchant, int maxEnchant)
	{
		_id = id;
		_count = count;
		_minEnchant = minEnchant;
		_maxEnchant = maxEnchant;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public long getCount()
	{
		return _count;
	}
	
	public int getMinEnchant()
	{
		return _minEnchant;
	}
	
	public int getMaxEnchant()
	{
		return _maxEnchant;
	}
}
