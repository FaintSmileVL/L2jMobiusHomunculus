package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.gameserver.GameTimeController;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class PAccuracyFinalizer implements IStatFunction
{
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		
		double baseValue = calcWeaponPlusBaseValue(creature, stat);
		
		// [Square(DEX)] * 5 + level + weapon hitbonus;
		final int level = creature.getLevel();
		baseValue += (Math.sqrt(creature.getDEX()) * 5) + level;
		if (level > 69)
		{
			baseValue += level - 69;
		}
		if (level > 77)
		{
			baseValue += 1;
		}
		if (level > 80)
		{
			baseValue += 2;
		}
		if (level > 87)
		{
			baseValue += 2;
		}
		if (level > 92)
		{
			baseValue += 1;
		}
		if (level > 97)
		{
			baseValue += 1;
		}
		
		if (creature.isPlayer())
		{
			// Enchanted gloves bonus
			baseValue += calcEnchantBodyPart(creature, Item.SLOT_GLOVES);
		}
		
		// Shadow sense
		if (GameTimeController.getInstance().isNight())
		{
			baseValue += creature.getStat().getAdd(Stat.HIT_AT_NIGHT, 0);
		}
		
		return Stat.defaultValue(creature, stat, baseValue);
	}
	
	@Override
	public double calcEnchantBodyPartBonus(int enchantLevel, boolean isBlessed)
	{
		if (isBlessed)
		{
			return (0.3 * Math.max(enchantLevel - 3, 0)) + (0.3 * Math.max(enchantLevel - 6, 0));
		}
		return (0.2 * Math.max(enchantLevel - 3, 0)) + (0.2 * Math.max(enchantLevel - 6, 0));
	}
}
