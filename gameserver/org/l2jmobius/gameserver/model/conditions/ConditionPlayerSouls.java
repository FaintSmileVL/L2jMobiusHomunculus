package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerSouls.
 */
public class ConditionPlayerSouls extends Condition
{
	private final int _souls;
	
	/**
	 * Instantiates a new condition player souls.
	 * @param souls the souls
	 */
	public ConditionPlayerSouls(int souls)
	{
		_souls = souls;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (effector.getActingPlayer() != null) && (effector.getActingPlayer().getChargedSouls() >= _souls);
	}
}
