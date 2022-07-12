package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.MonsterInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetAggro.
 * @author mkizub
 */
public class ConditionTargetAggro extends Condition
{
	private final boolean _isAggro;
	
	/**
	 * Instantiates a new condition target aggro.
	 * @param isAggro the is aggro
	 */
	public ConditionTargetAggro(boolean isAggro)
	{
		_isAggro = isAggro;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effected != null)
		{
			if (effected.isMonster())
			{
				return ((MonsterInstance) effected).isAggressive() == _isAggro;
			}
			if (effected.isPlayer())
			{
				return ((PlayerInstance) effected).getReputation() < 0;
			}
		}
		return false;
	}
}
