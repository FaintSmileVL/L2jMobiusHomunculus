package org.l2jmobius.gameserver.model.stats.finalizers;

import java.util.OptionalDouble;

import org.l2jmobius.gameserver.enums.AttributeType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.items.enchant.attribute.AttributeHolder;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.stats.IStatFunction;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class AttributeFinalizer implements IStatFunction
{
	private final AttributeType _type;
	private final boolean _isWeapon;
	
	public AttributeFinalizer(AttributeType type, boolean isWeapon)
	{
		_type = type;
		_isWeapon = isWeapon;
	}
	
	@Override
	public double calc(Creature creature, OptionalDouble base, Stat stat)
	{
		throwIfPresent(base);
		
		double baseValue = creature.getTemplate().getBaseValue(stat, 0);
		if (creature.isPlayable())
		{
			if (_isWeapon)
			{
				final ItemInstance weapon = creature.getActiveWeaponInstance();
				if (weapon != null)
				{
					final AttributeHolder weaponInstanceHolder = weapon.getAttribute(_type);
					if (weaponInstanceHolder != null)
					{
						baseValue += weaponInstanceHolder.getValue();
					}
					
					final AttributeHolder weaponHolder = weapon.getItem().getAttribute(_type);
					if (weaponHolder != null)
					{
						baseValue += weaponHolder.getValue();
					}
				}
			}
			else
			{
				final Inventory inventory = creature.getInventory();
				if (inventory != null)
				{
					for (ItemInstance item : inventory.getPaperdollItems(ItemInstance::isArmor))
					{
						final AttributeHolder weaponInstanceHolder = item.getAttribute(_type);
						if (weaponInstanceHolder != null)
						{
							baseValue += weaponInstanceHolder.getValue();
						}
						
						final AttributeHolder weaponHolder = item.getItem().getAttribute(_type);
						if (weaponHolder != null)
						{
							baseValue += weaponHolder.getValue();
						}
					}
				}
			}
		}
		return Stat.defaultValue(creature, stat, baseValue);
	}
}
