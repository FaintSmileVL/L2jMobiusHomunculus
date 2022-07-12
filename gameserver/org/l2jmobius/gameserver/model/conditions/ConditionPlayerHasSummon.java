package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Sdw
 */
public class ConditionPlayerHasSummon extends Condition
{
	private final boolean _hasSummon;
	
	public ConditionPlayerHasSummon(boolean hasSummon)
	{
		_hasSummon = hasSummon;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final PlayerInstance player = effector.getActingPlayer();
		if (player == null)
		{
			return false;
		}
		return _hasSummon == player.hasSummon();
	}
}
