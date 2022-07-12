package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.stats.BaseStat;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class MAttackFinalizer implements IStatFunction
{
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		
		double baseValue = calcWeaponBaseValue(creature, stat);
		baseValue += calcEnchantedItemBonus(creature, stat);
		if (creature.isPlayer())
		{
			// Enchanted chest bonus
			baseValue += calcEnchantBodyPart(creature, Item.SLOT_CHEST, Item.SLOT_FULL_ARMOR);
			// Bonus from Homunculus.
			baseValue += creature.getActingPlayer().getHomunculusAtkBonus();
		}
		
		if (Config.CHAMPION_ENABLE && creature.isChampion())
		{
			baseValue *= Config.CHAMPION_ATK;
		}
		if (creature.isRaid())
		{
			baseValue *= Config.RAID_MATTACK_MULTIPLIER;
		}
		
		// Calculate modifiers Magic Attack
		final double chaBonus = creature.isPlayer() ? BaseStat.CHA.calcBonus(creature) : 1.;
		baseValue *= Math.pow(BaseStat.INT.calcBonus(creature) * creature.getLevelMod() * chaBonus, 2.2072);
		return validateValue(creature, Stat.defaultValue(creature, stat, baseValue), 0, creature.isPlayer() ? Config.MAX_MATK : Double.MAX_VALUE);
	}
	
	@Override
	public double calcEnchantBodyPartBonus(int enchantLevel, boolean isBlessed)
	{
		if (isBlessed)
		{
			return (2 * Math.max(enchantLevel - 3, 0)) + (2 * Math.max(enchantLevel - 6, 0));
		}
		return (1.4 * Math.max(enchantLevel - 3, 0)) + (1.4 * Math.max(enchantLevel - 6, 0));
	}
}
