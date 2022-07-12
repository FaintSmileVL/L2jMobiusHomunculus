package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.zone.ZoneId;

/**
 * The Class ConditionPlayerLandingZone.
 * @author kerberos
 */
public class ConditionPlayerLandingZone extends Condition
{
	private final boolean _value;
	
	/**
	 * Instantiates a new condition player landing zone.
	 * @param value the value
	 */
	public ConditionPlayerLandingZone(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return effector.isInsideZone(ZoneId.LANDING) == _value;
	}
}
