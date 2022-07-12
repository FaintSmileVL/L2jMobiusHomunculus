package org.l2jmobius.gameserver.model.stats.functions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.conditions.Condition;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * Returns the initial value divided the function value, if the condition are met.
 * @author Zoey76
 */
public class FuncDiv extends AbstractFunction
{
	public FuncDiv(Stat stat, int order, Object owner, double value, Condition applayCond)
	{
		super(stat, order, owner, value, applayCond);
	}
	
	@Override
	public double calc(Creature effector, Creature effected, Skill skill, double initVal)
	{
		if ((getApplayCond() == null) || getApplayCond().test(effector, effected, skill))
		{
			try
			{
				return initVal / getValue();
			}
			catch (Exception e)
			{
				LOG.warning(FuncDiv.class.getSimpleName() + ": Division by zero: " + getValue() + "!");
			}
		}
		return initVal;
	}
}
