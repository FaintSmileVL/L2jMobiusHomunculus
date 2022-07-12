package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Sdw
 */
public class ConditionTargetCheckCrtEffect extends Condition
{
	private final boolean _isCrtEffect;
	
	public ConditionTargetCheckCrtEffect(boolean isCrtEffect)
	{
		_isCrtEffect = isCrtEffect;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effected.isNpc())
		{
			return ((Npc) effected).getTemplate().canBeCrt() == _isCrtEffect;
		}
		return true;
	}
}
