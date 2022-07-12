package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.stats.BaseStat;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class MAttackSpeedFinalizer implements IStatFunction
{
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		
		double baseValue = calcWeaponBaseValue(creature, stat);
		if (Config.CHAMPION_ENABLE && creature.isChampion())
		{
			baseValue *= Config.CHAMPION_SPD_ATK;
		}
		
		final double chaBonus = creature.isPlayer() ? BaseStat.CHA.calcBonus(creature) : 1.;
		final double witBonus = creature.getWIT() > 0 ? BaseStat.WIT.calcBonus(creature) : 1.;
		baseValue *= witBonus * chaBonus;
		return validateValue(creature, defaultValue(creature, stat, baseValue), 1, creature.isPlayer() ? Config.MAX_MATK_SPEED : Double.MAX_VALUE);
	}
	
	private double defaultValue(Creature creature, Stat stat, double baseValue)
	{
		final double mul = Math.max(creature.getStat().getMul(stat), 0.7);
		final double add = creature.getStat().getAdd(stat);
		return (baseValue * mul) + add + creature.getStat().getMoveTypeValue(stat, creature.getMoveType());
	}
}
