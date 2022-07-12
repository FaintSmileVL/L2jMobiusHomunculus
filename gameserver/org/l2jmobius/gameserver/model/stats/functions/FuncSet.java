package org.l2jmobius.gameserver.model.stats.functions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.conditions.Condition;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * Returns the function value, if the condition are met.
 * @author Zoey76
 */
public class FuncSet extends AbstractFunction
{
	public FuncSet(Stat stat, int order, Object owner, double value, Condition applayCond)
	{
		super(stat, order, owner, value, applayCond);
	}
	
	@Override
	public double calc(Creature effector, Creature effected, Skill skill, double initVal)
	{
		if ((getApplayCond() == null) || getApplayCond().test(effector, effected, skill))
		{
			return getValue();
		}
		return initVal;
	}
}
