package org.l2jmobius.gameserver.model.stats.functions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.conditions.Condition;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * Returns the initial value minus the function value, if the condition are met.
 * @author Zoey76
 */
public class FuncSub extends AbstractFunction
{
	public FuncSub(Stat stat, int order, Object owner, double value, Condition applayCond)
	{
		super(stat, order, owner, value, applayCond);
	}
	
	@Override
	public double calc(Creature effector, Creature effected, Skill skill, double initVal)
	{
		if ((getApplayCond() == null) || getApplayCond().test(effector, effected, skill))
		{
			return initVal - getValue();
		}
		return initVal;
	}
}
