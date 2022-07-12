package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author NviX
 */
public class ConditionPlayerSymbolSealPoints extends Condition
{
	private final int _points;
	
	/**
	 * Instantiates a new condition player Symbol Seal points.
	 * @param points the Symbol Seal Points
	 */
	public ConditionPlayerSymbolSealPoints(int points)
	{
		_points = points;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final PlayerInstance player = effector.getActingPlayer();
		return (player != null) && (player.getSymbolSealPoints() < _points);
	}
}
