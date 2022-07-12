package org.l2jmobius.gameserver.model.holders;

import java.util.List;

import util.Rnd;

/**
 * A DTO for items; contains item ID, count and chance.<br>
 * Complemented by {@link QuestItemHolder}.
 * @author xban1x
 */
public class ItemChanceHolder extends ItemHolder
{
	private final double _chance;
	private final byte _enchantmentLevel;
	private final boolean _maintainIngredient;
	
	public ItemChanceHolder(int id, double chance)
	{
		this(id, chance, 1);
	}
	
	public ItemChanceHolder(int id, double chance, long count)
	{
		super(id, count);
		_chance = chance;
		_enchantmentLevel = 0;
		_maintainIngredient = false;
	}
	
	public ItemChanceHolder(int id, double chance, long count, byte enchantmentLevel)
	{
		super(id, count);
		_chance = chance;
		_enchantmentLevel = enchantmentLevel;
		_maintainIngredient = false;
	}
	
	public ItemChanceHolder(int id, double chance, long count, byte enchantmentLevel, boolean maintainIngredient)
	{
		super(id, count);
		_chance = chance;
		_enchantmentLevel = enchantmentLevel;
		_maintainIngredient = maintainIngredient;
	}
	
	/**
	 * Gets the chance.
	 * @return the drop chance of the item contained in this object
	 */
	public double getChance()
	{
		return _chance;
	}
	
	/**
	 * Gets the enchant level.
	 * @return the enchant level of the item contained in this object
	 */
	public byte getEnchantmentLevel()
	{
		return _enchantmentLevel;
	}
	
	public boolean isMaintainIngredient()
	{
		return _maintainIngredient;
	}
	
	/**
	 * Calculates a cumulative chance of all given holders. If all holders' chance sum up to 100% or above, there is 100% guarantee a holder will be selected.
	 * @param holders list of holders to calculate chance from.
	 * @return {@code ItemChanceHolder} of the successful random roll or {@code null} if there was no lucky holder selected.
	 */
	public static ItemChanceHolder getRandomHolder(List<ItemChanceHolder> holders)
	{
		double itemRandom = 100 * Rnd.nextDouble();
		for (ItemChanceHolder holder : holders)
		{
			// Any mathmatical expression including NaN will result in either NaN or 0 of converted to something other than double.
			// We would usually want to skip calculating any holders that include NaN as a chance, because that ruins the overall process.
			if (!Double.isNaN(holder.getChance()))
			{
				// Calculate chance
				if (holder.getChance() > itemRandom)
				{
					return holder;
				}
				
				itemRandom -= holder.getChance();
			}
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		return "[" + getClass().getSimpleName() + "] ID: " + getId() + ", count: " + getCount() + ", chance: " + _chance;
	}
}
