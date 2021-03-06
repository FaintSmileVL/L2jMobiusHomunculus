package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.stats.BaseStat;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class MDefenseFinalizer implements IStatFunction
{
	private static final int[] SLOTS =
	{
		Inventory.PAPERDOLL_LFINGER,
		Inventory.PAPERDOLL_RFINGER,
		Inventory.PAPERDOLL_LEAR,
		Inventory.PAPERDOLL_REAR,
		Inventory.PAPERDOLL_NECK
	};
	
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		double baseValue = creature.getTemplate().getBaseValue(stat, 0);
		if (creature.isPet())
		{
			final PetInstance pet = (PetInstance) creature;
			baseValue = pet.getPetLevelData().getPetMDef();
		}
		baseValue += calcEnchantedItemBonus(creature, stat);
		
		final Inventory inv = creature.getInventory();
		if (inv != null)
		{
			for (ItemInstance item : inv.getPaperdollItems())
			{
				baseValue += item.getItem().getStats(stat, 0);
			}
		}
		
		if (creature.isPlayer())
		{
			final PlayerInstance player = creature.getActingPlayer();
			for (int slot : SLOTS)
			{
				if (!player.getInventory().isPaperdollSlotEmpty(slot))
				{
					final int defaultStatValue = player.getTemplate().getBaseDefBySlot(slot);
					baseValue -= creature.getTransformation().map(transform -> transform.getBaseDefBySlot(player, slot)).orElse(defaultStatValue);
				}
			}
			
			baseValue *= BaseStat.CHA.calcBonus(creature);
			
			// Bonus from Homunculus.
			baseValue += player.getHomunculusDefBonus();
		}
		else if (creature.isPet() && (creature.getInventory().getPaperdollObjectId(Inventory.PAPERDOLL_NECK) != 0))
		{
			baseValue -= 13;
		}
		if (creature.isRaid())
		{
			baseValue *= Config.RAID_MDEFENCE_MULTIPLIER;
		}
		
		final double bonus = creature.getMEN() > 0 ? BaseStat.MEN.calcBonus(creature) : 1.;
		baseValue *= bonus * creature.getLevelMod();
		return defaultValue(creature, stat, baseValue);
	}
	
	private double defaultValue(Creature creature, Stat stat, double baseValue)
	{
		final double mul = Math.max(creature.getStat().getMul(stat), 0.5);
		final double add = creature.getStat().getAdd(stat);
		return (baseValue * mul) + add + creature.getStat().getMoveTypeValue(stat, creature.getMoveType());
	}
}
