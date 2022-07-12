package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.enums.Race;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetRace.
 * @author Zealar
 */
public class ConditionTargetRace extends Condition
{
	private final Race _race;
	
	/**
	 * Instantiates a new condition target race.
	 * @param race containing the allowed race.
	 */
	public ConditionTargetRace(Race race)
	{
		_race = race;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return _race == effected.getRace();
	}
}
