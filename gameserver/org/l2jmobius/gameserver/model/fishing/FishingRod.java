package org.l2jmobius.gameserver.model.fishing;

/**
 * @author Zarcos
 */
public class FishingRod
{
	private final int _itemId;
	private final int _reduceFishingTime;
	private final float _xpMultiplier;
	private final float _spMultiplier;
	
	public FishingRod(int itemId, int reduceFishingTime, float xpMultiplier, float spMultiplier)
	{
		_itemId = itemId;
		_reduceFishingTime = reduceFishingTime;
		_xpMultiplier = xpMultiplier;
		_spMultiplier = spMultiplier;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public int getReduceFishingTime()
	{
		return _reduceFishingTime;
	}
	
	public float getXpMultiplier()
	{
		return _xpMultiplier;
	}
	
	public float getSpMultiplier()
	{
		return _spMultiplier;
	}
}
