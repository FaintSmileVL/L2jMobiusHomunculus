package org.l2jmobius.gameserver.handler;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.skills.targets.AffectObject;

/**
 * @author Nik
 */
public interface IAffectObjectHandler
{
	/**
	 * Checks if the rules for the given affect object type are accepted or not.
	 * @param creature
	 * @param target
	 * @return {@code true} if target should be accepted, {@code false} otherwise
	 **/
	boolean checkAffectedObject(Creature creature, Creature target);
	
	Enum<AffectObject> getAffectObjectType();
}
