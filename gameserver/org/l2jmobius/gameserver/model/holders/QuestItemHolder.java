package org.l2jmobius.gameserver.model.holders;

/**
 * A DTO for items; contains item ID, count and chance.<br>
 * Complemented by {@link ItemChanceHolder}.
 * @author xban1x
 */
public class QuestItemHolder extends ItemHolder
{
	private final int _chance;
	
	public QuestItemHolder(int id, int chance)
	{
		this(id, chance, 1);
	}
	
	public QuestItemHolder(int id, int chance, long count)
	{
		super(id, count);
		_chance = chance;
	}
	
	/**
	 * Gets the chance.
	 * @return the drop chance of the item contained in this object
	 */
	public int getChance()
	{
		return _chance;
	}
	
	@Override
	public String toString()
	{
		return "[" + getClass().getSimpleName() + "] ID: " + getId() + ", count: " + getCount() + ", chance: " + _chance;
	}
}
