package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerCp.
 */
public class ConditionPlayerCp extends Condition
{
	private final int _cp;
	
	/**
	 * Instantiates a new condition player cp.
	 * @param cp the cp
	 */
	public ConditionPlayerCp(int cp)
	{
		_cp = cp;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (effector != null) && (((effector.getCurrentCp() * 100) / effector.getMaxCp()) >= _cp);
	}
}
