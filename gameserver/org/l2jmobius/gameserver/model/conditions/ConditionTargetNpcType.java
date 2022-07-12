package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetNpcType.
 */
public class ConditionTargetNpcType extends Condition
{
	private final InstanceType[] _npcType;
	
	/**
	 * Instantiates a new condition target npc type.
	 * @param type the type
	 */
	public ConditionTargetNpcType(InstanceType[] type)
	{
		_npcType = type;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effected == null)
		{
			return false;
		}
		return effected.getInstanceType().isTypes(_npcType);
	}
}
