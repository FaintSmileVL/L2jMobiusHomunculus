package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * Player Can Summon condition implementation.
 * @author Sdw
 */
public class ConditionPlayerCanSummonServitor extends Condition
{
	private final boolean _value;
	
	public ConditionPlayerCanSummonServitor(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final PlayerInstance player = effector.getActingPlayer();
		if (player == null)
		{
			return false;
		}
		
		boolean canSummon = true;
		if (player.isFlyingMounted() || player.isMounted() || player.inObserverMode() || player.isTeleporting())
		{
			canSummon = false;
		}
		else if (player.getServitors().size() >= 4)
		{
			canSummon = false;
		}
		return canSummon == _value;
	}
}