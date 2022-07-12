package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;

/**
 * @author Sdw
 */
public interface ICondition
{
	boolean test(Creature creature, WorldObject object);
}
