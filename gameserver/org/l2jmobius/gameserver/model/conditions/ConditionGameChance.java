package org.l2jmobius.gameserver.model.conditions;

import util.Rnd;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionGameChance.
 * @author Advi
 */
public class ConditionGameChance extends Condition
{
	private final int _chance;
	
	/**
	 * Instantiates a new condition game chance.
	 * @param chance the chance
	 */
	public ConditionGameChance(int chance)
	{
		_chance = chance;
	}
	
	/**
	 * Test impl.
	 * @return true, if successful
	 */
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return Rnd.get(100) < _chance;
	}
}
