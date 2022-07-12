package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerCharges.
 */
public class ConditionPlayerCharges extends Condition
{
	private final int _charges;
	
	/**
	 * Instantiates a new condition player charges.
	 * @param charges the charges
	 */
	public ConditionPlayerCharges(int charges)
	{
		_charges = charges;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (effector.getActingPlayer() != null) && (effector.getActingPlayer().getCharges() >= _charges);
	}
}
