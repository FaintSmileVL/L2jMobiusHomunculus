package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.stats.BaseStat;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class PDefenseFinalizer implements IStatFunction
{
	private static final int[] SLOTS =
	{
		Inventory.PAPERDOLL_CHEST,
		Inventory.PAPERDOLL_LEGS,
		Inventory.PAPERDOLL_HEAD,
		Inventory.PAPERDOLL_FEET,
		Inventory.PAPERDOLL_GLOVES,
		Inventory.PAPERDOLL_UNDER,
		Inventory.PAPERDOLL_CLOAK,
		Inventory.PAPERDOLL_HAIR
	};
	
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		double baseValue = creature.getTemplate().getBaseValue(stat, 0);
		if (creature.isPet())
		{
			final PetInstance pet = (PetInstance) creature;
			baseValue = pet.getPetLevelData().getPetPDef();
		}
		baseValue += calcEnchantedItemBonus(creature, stat);
		
		final Inventory inv = creature.getInventory();
		if (inv != null)
		{
			for (ItemInstance item : inv.getPaperdollItems())
			{
				baseValue += item.getItem().getStats(stat, 0);
			}
			
			if (creature.isPlayer())
			{
				final PlayerInstance player = creature.getActingPlayer();
				for (int slot : SLOTS)
				{
					if (!inv.isPaperdollSlotEmpty(slot) || //
						((slot == Inventory.PAPERDOLL_LEGS) && !inv.isPaperdollSlotEmpty(Inventory.PAPERDOLL_CHEST) && (inv.getPaperdollItem(Inventory.PAPERDOLL_CHEST).getItem().getBodyPart() == Item.SLOT_FULL_ARMOR)))
					{
						final int defaultStatValue = player.getTemplate().getBaseDefBySlot(slot);
						baseValue -= creature.getTransformation().map(transform -> transform.getBaseDefBySlot(player, slot)).orElse(defaultStatValue);
					}
				}
				baseValue *= BaseStat.CHA.calcBonus(creature);
				
				// Bonus from Homunculus.
				baseValue += player.getHomunculusDefBonus();
			}
		}
		if (creature.isRaid())
		{
			baseValue *= Config.RAID_PDEFENCE_MULTIPLIER;
		}
		if (creature.getLevel() > 0)
		{
			baseValue *= creature.getLevelMod();
		}
		
		return defaultValue(creature, stat, baseValue);
	}
	
	private double defaultValue(Creature creature, Stat stat, double baseValue)
	{
		final double mul = Math.max(creature.getStat().getMul(stat), 0.5);
		final double add = creature.getStat().getAdd(stat);
		return (baseValue * mul) + add + creature.getStat().getMoveTypeValue(stat, creature.getMoveType());
	}
}
