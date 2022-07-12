package org.l2jmobius.gameserver.model.conditions;

import java.util.List;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerHasClanHall.
 * @author MrPoke
 */
public class ConditionPlayerHasClanHall extends Condition
{
	private final List<Integer> _clanHall;
	
	/**
	 * Instantiates a new condition player has clan hall.
	 * @param clanHall the clan hall
	 */
	public ConditionPlayerHasClanHall(List<Integer> clanHall)
	{
		_clanHall = clanHall;
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
			return ((_clanHall.size() == 1) && (_clanHall.get(0) == 0));
		}
		
		// All Clan Hall
		if ((_clanHall.size() == 1) && (_clanHall.get(0) == -1))
		{
			return clan.getHideoutId() > 0;
		}
		return _clanHall.contains(clan.getHideoutId());
	}
}
