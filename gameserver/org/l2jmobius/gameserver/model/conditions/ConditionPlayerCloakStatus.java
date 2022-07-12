package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerCloakStatus.
 */
public class ConditionPlayerCloakStatus extends Condition
{
	private final boolean _value;
	
	/**
	 * Instantiates a new condition player cloak status.
	 * @param value the value
	 */
	public ConditionPlayerCloakStatus(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (effector.getActingPlayer() != null) && (effector.getActingPlayer().getInventory().canEquipCloak() == _value);
	}
}
