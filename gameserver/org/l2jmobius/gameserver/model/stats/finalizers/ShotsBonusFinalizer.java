package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class ShotsBonusFinalizer implements IStatFunction
{
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		
		double baseValue = 1;
		final PlayerInstance player = creature.getActingPlayer();
		if (player != null)
		{
			final ItemInstance weapon = player.getActiveWeaponInstance();
			if ((weapon != null) && weapon.isEnchanted())
			{
				baseValue += (weapon.getEnchantLevel() * 0.7) / 100;
			}
			if (player.getActiveRubyJewel() != null)
			{
				baseValue += player.getActiveRubyJewel().getBonus();
			}
		}
		return Stat.defaultValue(creature, stat, baseValue);
	}
}
