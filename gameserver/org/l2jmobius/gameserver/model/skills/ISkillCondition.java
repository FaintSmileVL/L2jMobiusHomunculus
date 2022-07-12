package org.l2jmobius.gameserver.model.skills;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;

/**
 * @author NosBit
 */
public interface ISkillCondition
{
	boolean canUse(Creature caster, Skill skill, WorldObject target);
}
