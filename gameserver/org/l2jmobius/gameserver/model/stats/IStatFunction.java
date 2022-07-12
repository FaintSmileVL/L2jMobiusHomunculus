package org.l2jmobius.gameserver.model.stats;

import java.util.OptionalDouble;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.PlayerCondOverride;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.actor.transform.TransformType;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.items.type.CrystalType;
import org.l2jmobius.gameserver.model.items.type.WeaponType;

/**
 * @author UnAfraid
 */
@FunctionalInterface
public interface IStatFunction
{
	default void throwIfPresent(OptionalDouble base)
	{
		if (base.isPresent())
		{
			throw new IllegalArgumentException("base should not be set for " + getClass().getSimpleName());
		}
	}
	
	default double calcEnchantBodyPart(Creature creature, int... slots)
	{
		double value = 0;
		for (int slot : slots)
		{
			final ItemInstance item = creature.getInventory().getPaperdollItemByItemId(slot);
			if ((item != null) && (item.getEnchantLevel() >= 4) && (item.getItem().getCrystalTypePlus() == CrystalType.R))
			{
				value += calcEnchantBodyPartBonus(item.getEnchantLevel(), item.getItem().isBlessed());
			}
		}
		return value;
	}
	
	default double calcEnchantBodyPartBonus(int enchantLevel, boolean isBlessed)
	{
		return 0;
	}
	
	default double calcWeaponBaseValue(Creature creature, Stat stat)
	{
		final double baseTemplateValue = creature.getTemplate().getBaseValue(stat, 0);
		double baseValue = creature.getTransformation().map(transform -> transform.getStats(creature, stat, baseTemplateValue)).orElse(baseTemplateValue);
		if (creature.isPet())
		{
			final PetInstance pet = (PetInstance) creature;
			final ItemInstance weapon = pet.getActiveWeaponInstance();
			final double baseVal = stat == Stat.PHYSICAL_ATTACK ? pet.getPetLevelData().getPetPAtk() : stat == Stat.MAGIC_ATTACK ? pet.getPetLevelData().getPetMAtk() : baseTemplateValue;
			baseValue = baseVal + (weapon != null ? weapon.getItem().getStats(stat, baseVal) : 0);
		}
		else if (creature.isPlayer() && (!creature.isTransformed() || (creature.getTransformation().get().getType() == TransformType.COMBAT) || (creature.getTransformation().get().getType() == TransformType.MODE_CHANGE)))
		{
			final ItemInstance weapon = creature.getActiveWeaponInstance();
			baseValue = (weapon != null ? weapon.getItem().getStats(stat, baseTemplateValue) : baseTemplateValue);
		}
		
		return baseValue;
	}
	
	default double calcWeaponPlusBaseValue(Creature creature, Stat stat)
	{
		final double baseTemplateValue = creature.getTemplate().getBaseValue(stat, 0);
		double baseValue = creature.getTransformation().filter(transform -> !transform.isStance()).map(transform -> transform.getStats(creature, stat, baseTemplateValue)).orElse(baseTemplateValue);
		
		if (creature.isPlayable())
		{
			final Inventory inv = creature.getInventory();
			if (inv != null)
			{
				baseValue += inv.getPaperdollCache().getStats(stat);
			}
		}
		
		return baseValue;
	}
	
	default double calcEnchantedItemBonus(Creature creature, Stat stat)
	{
		if (!creature.isPlayer())
		{
			return 0;
		}
		
		double value = 0;
		for (ItemInstance equippedItem : creature.getInventory().getPaperdollItems(ItemInstance::isEnchanted))
		{
			final Item item = equippedItem.getItem();
			final long bodypart = item.getBodyPart();
			if ((bodypart == Item.SLOT_HAIR) || //
				(bodypart == Item.SLOT_HAIR2) || //
				(bodypart == Item.SLOT_HAIRALL))
			{
				// TODO: Item after enchant shows pDef, but scroll says mDef increase.
				if ((stat != Stat.PHYSICAL_DEFENCE) && (stat != Stat.MAGICAL_DEFENCE))
				{
					continue;
				}
			}
			else if (item.getStats(stat, 0) <= 0)
			{
				continue;
			}
			
			final double blessedBonus = item.isBlessed() ? 1.5 : 1;
			int enchant = equippedItem.getEnchantLevel();
			
			if (creature.getActingPlayer().isInOlympiadMode())
			{
				if (item.isWeapon())
				{
					if ((Config.ALT_OLY_WEAPON_ENCHANT_LIMIT >= 0) && (enchant > Config.ALT_OLY_WEAPON_ENCHANT_LIMIT))
					{
						enchant = Config.ALT_OLY_WEAPON_ENCHANT_LIMIT;
					}
				}
				else
				{
					if ((Config.ALT_OLY_ARMOR_ENCHANT_LIMIT >= 0) && (enchant > Config.ALT_OLY_ARMOR_ENCHANT_LIMIT))
					{
						enchant = Config.ALT_OLY_ARMOR_ENCHANT_LIMIT;
					}
				}
			}
			
			if ((stat == Stat.MAGICAL_DEFENCE) || (stat == Stat.PHYSICAL_DEFENCE))
			{
				value += calcEnchantDefBonus(equippedItem, blessedBonus, enchant);
			}
			else if (stat == Stat.MAGIC_ATTACK)
			{
				value += calcEnchantMatkBonus(equippedItem, blessedBonus, enchant);
			}
			else if ((stat == Stat.PHYSICAL_ATTACK) && equippedItem.isWeapon())
			{
				value += calcEnchantedPAtkBonus(equippedItem, blessedBonus, enchant);
			}
		}
		return value;
	}
	
	/**
	 * @param item
	 * @param blessedBonus
	 * @param enchant
	 * @return
	 */
	static double calcEnchantDefBonus(ItemInstance item, double blessedBonus, int enchant)
	{
		switch (item.getItem().getCrystalTypePlus())
		{
			case R:
			{
				return ((2 * blessedBonus * enchant) + (6 * blessedBonus * Math.max(0, enchant - 3)));
			}
			default:
			{
				return enchant + (3 * Math.max(0, enchant - 3));
			}
		}
	}
	
	/**
	 * @param item
	 * @param blessedBonus
	 * @param enchant
	 * @return
	 */
	static double calcEnchantMatkBonus(ItemInstance item, double blessedBonus, int enchant)
	{
		switch (item.getItem().getCrystalTypePlus())
		{
			case R:
			{
				return ((5 * blessedBonus * enchant) + (10 * blessedBonus * Math.max(0, enchant - 3)));
			}
			case S:
			{
				// M. Atk. increases by 4 for all weapons.
				// Starting at +4, M. Atk. bonus double.
				return (4 * enchant) + (8 * Math.max(0, enchant - 3));
			}
			case A:
			case B:
			case C:
			{
				// M. Atk. increases by 3 for all weapons.
				// Starting at +4, M. Atk. bonus double.
				return (3 * enchant) + (6 * Math.max(0, enchant - 3));
			}
			default:
			{
				// M. Atk. increases by 2 for all weapons. Starting at +4, M. Atk. bonus double.
				// Starting at +4, M. Atk. bonus double.
				return (2 * enchant) + (4 * Math.max(0, enchant - 3));
			}
		}
	}
	
	/**
	 * @param item
	 * @param blessedBonus
	 * @param enchant
	 * @return
	 */
	static double calcEnchantedPAtkBonus(ItemInstance item, double blessedBonus, int enchant)
	{
		switch (item.getItem().getCrystalTypePlus())
		{
			case R:
			{
				if ((item.getWeaponItem().getBodyPart() == Item.SLOT_LR_HAND) && (item.getWeaponItem().getItemType() != WeaponType.POLE))
				{
					if (item.getWeaponItem().getItemType().isRanged())
					{
						return (12 * blessedBonus * enchant) + (24 * blessedBonus * Math.max(0, enchant - 3));
					}
					return (7 * blessedBonus * enchant) + (14 * blessedBonus * Math.max(0, enchant - 3));
				}
				return (6 * blessedBonus * enchant) + (12 * blessedBonus * Math.max(0, enchant - 3));
			}
			case S:
			{
				if ((item.getWeaponItem().getBodyPart() == Item.SLOT_LR_HAND) && (item.getWeaponItem().getItemType() != WeaponType.POLE))
				{
					if (item.getWeaponItem().getItemType().isRanged())
					{
						// P. Atk. increases by 10 for bows.
						// Starting at +4, P. Atk. bonus double.
						return (10 * enchant) + (20 * Math.max(0, enchant - 3));
					}
					// P. Atk. increases by 6 for two-handed swords, two-handed blunts, dualswords, and two-handed combat weapons.
					// Starting at +4, P. Atk. bonus double.
					return (6 * enchant) + (12 * Math.max(0, enchant - 3));
				}
				// P. Atk. increases by 5 for one-handed swords, one-handed blunts, daggers, spears, and other weapons.
				// Starting at +4, P. Atk. bonus double.
				return (5 * enchant) + (10 * Math.max(0, enchant - 3));
			}
			case A:
			{
				if ((item.getWeaponItem().getBodyPart() == Item.SLOT_LR_HAND) && (item.getWeaponItem().getItemType() != WeaponType.POLE))
				{
					if (item.getWeaponItem().getItemType().isRanged())
					{
						// P. Atk. increases by 8 for bows.
						// Starting at +4, P. Atk. bonus double.
						return (8 * enchant) + (16 * Math.max(0, enchant - 3));
					}
					// P. Atk. increases by 5 for two-handed swords, two-handed blunts, dualswords, and two-handed combat weapons.
					// Starting at +4, P. Atk. bonus double.
					return (5 * enchant) + (10 * Math.max(0, enchant - 3));
				}
				// P. Atk. increases by 4 for one-handed swords, one-handed blunts, daggers, spears, and other weapons.
				// Starting at +4, P. Atk. bonus double.
				return (4 * enchant) + (8 * Math.max(0, enchant - 3));
			}
			case B:
			case C:
			{
				if ((item.getWeaponItem().getBodyPart() == Item.SLOT_LR_HAND) && (item.getWeaponItem().getItemType() != WeaponType.POLE))
				{
					if (item.getWeaponItem().getItemType().isRanged())
					{
						// P. Atk. increases by 6 for bows.
						// Starting at +4, P. Atk. bonus double.
						return (6 * enchant) + (12 * Math.max(0, enchant - 3));
					}
					// P. Atk. increases by 4 for two-handed swords, two-handed blunts, dualswords, and two-handed combat weapons.
					// Starting at +4, P. Atk. bonus double.
					return (4 * enchant) + (8 * Math.max(0, enchant - 3));
				}
				// P. Atk. increases by 3 for one-handed swords, one-handed blunts, daggers, spears, and other weapons.
				// Starting at +4, P. Atk. bonus double.
				return (3 * enchant) + (6 * Math.max(0, enchant - 3));
			}
			default:
			{
				if (item.getWeaponItem().getItemType().isRanged())
				{
					// Bows increase by 4.
					// Starting at +4, P. Atk. bonus double.
					return (4 * enchant) + (8 * Math.max(0, enchant - 3));
				}
				// P. Atk. increases by 2 for all weapons with the exception of bows.
				// Starting at +4, P. Atk. bonus double.
				return (2 * enchant) + (4 * Math.max(0, enchant - 3));
			}
		}
	}
	
	default double validateValue(Creature creature, double value, double minValue, double maxValue)
	{
		if ((value > maxValue) && !creature.canOverrideCond(PlayerCondOverride.MAX_STATS_VALUE))
		{
			return maxValue;
		}
		
		return Math.max(minValue, value);
	}
	
	double calc(Creature creature, OptionalDouble base, Stat stat);
}