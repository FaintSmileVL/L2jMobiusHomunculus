package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerSiegeSide.
 */
public class ConditionPlayerSiegeSide extends Condition
{
	private final int _siegeSide;
	
	/**
	 * Instantiates a new condition player siege side.
	 * @param side the side
	 */
	public ConditionPlayerSiegeSide(int side)
	{
		_siegeSide = side;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effector.getActingPlayer() == null)
		{
			return false;
		}
		return effector.getActingPlayer().getSiegeSide() == _siegeSide;
	}
}
