package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerHasFort.
 * @author MrPoke
 */
public class ConditionPlayerHasFort extends Condition
{
	private final int _fort;
	
	/**
	 * Instantiates a new condition player has fort.
	 * @param fort the fort
	 */
	public ConditionPlayerHasFort(int fort)
	{
		_fort = fort;
	}
	
	/**
	 * Test impl.
	 * @return true, if successful
	 */
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effector.getActingPlayer() == null)
		{
			return false;
		}
		
		final Clan clan = effector.getActingPlayer().getClan();
		if (clan == null)
		{
			return _fort == 0;
		}
		
		// Any fortress
		if (_fort == -1)
		{
			return clan.getFortId() > 0;
		}
		return clan.getFortId() == _fort;
	}
}
