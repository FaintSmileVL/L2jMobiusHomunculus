package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerBaseStats.
 * @author mkizub
 */
public class ConditionPlayerBaseStats extends Condition
{
	private final BaseStat _stat;
	private final int _value;
	
	/**
	 * Instantiates a new condition player base stats.
	 * @param creature the player
	 * @param stat the stat
	 * @param value the value
	 */
	public ConditionPlayerBaseStats(Creature creature, BaseStat stat, int value)
	{
		super();
		_stat = stat;
		_value = value;
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
		final PlayerInstance player = effector.getActingPlayer();
		switch (_stat)
		{
			case Int:
			{
				return player.getINT() >= _value;
			}
			case Str:
			{
				return player.getSTR() >= _value;
			}
			case Con:
			{
				return player.getCON() >= _value;
			}
			case Dex:
			{
				return player.getDEX() >= _value;
			}
			case Men:
			{
				return player.getMEN() >= _value;
			}
			case Wit:
			{
				return player.getWIT() >= _value;
			}
		}
		return false;
	}
}

enum BaseStat
{
	Int,
	Str,
	Con,
	Dex,
	Men,
	Wit
}
