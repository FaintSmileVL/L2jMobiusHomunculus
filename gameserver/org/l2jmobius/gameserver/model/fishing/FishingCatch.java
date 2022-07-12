package org.l2jmobius.gameserver.model.fishing;

/**
 * @author Zarcos
 */
public class FishingCatch
{
	private final int _itemId;
	private final float _chance;
	private final float _multiplier;
	
	public FishingCatch(int itemId, float chance, float multiplier)
	{
		_itemId = itemId;
		_chance = chance;
		_multiplier = multiplier;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public float getChance()
	{
		return _chance;
	}
	
	public float getMultiplier()
	{
		return _multiplier;
	}
}
