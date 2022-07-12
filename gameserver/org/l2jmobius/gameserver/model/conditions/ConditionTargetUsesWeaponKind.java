package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.Weapon;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetUsesWeaponKind.
 * @author mkizub
 */
public class ConditionTargetUsesWeaponKind extends Condition
{
	private final int _weaponMask;
	
	/**
	 * Instantiates a new condition target uses weapon kind.
	 * @param weaponMask the weapon mask
	 */
	public ConditionTargetUsesWeaponKind(int weaponMask)
	{
		_weaponMask = weaponMask;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effected == null)
		{
			return false;
		}
		
		final Weapon weapon = effected.getActiveWeaponItem();
		if (weapon == null)
		{
			return false;
		}
		
		return (weapon.getItemType().mask() & _weaponMask) != 0;
	}
}
