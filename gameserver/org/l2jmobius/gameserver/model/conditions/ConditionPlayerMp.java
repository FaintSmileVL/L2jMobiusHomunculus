package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerMp.
 */
public class ConditionPlayerMp extends Condition
{
	private final int _mp;
	
	/**
	 * Instantiates a new condition player mp.
	 * @param mp the mp
	 */
	public ConditionPlayerMp(int mp)
	{
		_mp = mp;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return ((effector.getCurrentMp() * 100) / effector.getMaxMp()) <= _mp;
	}
}
