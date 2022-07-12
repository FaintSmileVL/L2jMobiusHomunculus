package org.l2jmobius.gameserver.model.conditions;

import java.util.List;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetClassIdRestriction.
 */
public class ConditionTargetClassIdRestriction extends Condition
{
	private final List<Integer> _classIds;
	
	/**
	 * Instantiates a new condition target class id restriction.
	 * @param classId the class id
	 */
	public ConditionTargetClassIdRestriction(List<Integer> classId)
	{
		_classIds = classId;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return effected.isPlayer() && (_classIds.contains(effected.getActingPlayer().getClassId().getId()));
	}
}
