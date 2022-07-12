package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.enums.PlayerState;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerState.
 * @author mkizub
 */
public class ConditionPlayerState extends Condition
{
	private final PlayerState _check;
	private final boolean _required;
	
	/**
	 * Instantiates a new condition player state.
	 * @param check the player state to be verified.
	 * @param required the required value.
	 */
	public ConditionPlayerState(PlayerState check, boolean required)
	{
		_check = check;
		_required = required;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final PlayerInstance player = effector.getActingPlayer();
		switch (_check)
		{
			case RESTING:
			{
				if (player != null)
				{
					return (player.isSitting() == _required);
				}
				return !_required;
			}
			case MOVING:
			{
				return effector.isMoving() == _required;
			}
			case RUNNING:
			{
				return effector.isRunning() == _required;
			}
			case STANDING:
			{
				if (player != null)
				{
					return (_required != (player.isSitting() || player.isMoving()));
				}
				return (_required != effector.isMoving());
			}
			case FLYING:
			{
				return (effector.isFlying() == _required);
			}
			case BEHIND:
			{
				return (effector.isBehind(effected) == _required);
			}
			case FRONT:
			{
				return (effector.isInFrontOf(effected) == _required);
			}
			case CHAOTIC:
			{
				if (player != null)
				{
					return ((player.getReputation() < 0) == _required);
				}
				return !_required;
			}
			case OLYMPIAD:
			{
				if (player != null)
				{
					return (player.isInOlympiadMode() == _required);
				}
				return !_required;
			}
		}
		return !_required;
	}
}
