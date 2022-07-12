package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.AbnormalType;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * Condition implementation to verify player's abnormal type and level.
 * @author Zoey76
 */
public class ConditionPlayerCheckAbnormal extends Condition
{
	private final AbnormalType _type;
	private final int _level;
	
	/**
	 * Instantiates a new condition player check abnormal.
	 * @param type the abnormal type
	 */
	public ConditionPlayerCheckAbnormal(AbnormalType type)
	{
		_type = type;
		_level = -1;
	}
	
	/**
	 * Instantiates a new condition player check abnormal.
	 * @param type the abnormal type
	 * @param level the abnormal level
	 */
	public ConditionPlayerCheckAbnormal(AbnormalType type, int level)
	{
		_type = type;
		_level = level;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (_level == -1)
		{
			return effector.getEffectList().hasAbnormalType(_type);
		}
		return effector.getEffectList().hasAbnormalType(_type, info -> _level >= info.getSkill().getAbnormalLevel());
	}
}
