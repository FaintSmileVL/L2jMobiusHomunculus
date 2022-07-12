package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author St3eT
 */
public class OnPlayerAbilityPointsChanged implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _newAbilityPoints;
	private final int _oldAbilityPoints;
	
	public OnPlayerAbilityPointsChanged(PlayerInstance player, int newAbilityPoints, int oldAbilityPoints)
	{
		_player = player;
		_newAbilityPoints = newAbilityPoints;
		_oldAbilityPoints = oldAbilityPoints;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public long getNewAbilityPoints()
	{
		return _newAbilityPoints;
	}
	
	public long getOldAbilityPoints()
	{
		return _oldAbilityPoints;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_ABILITY_POINTS_CHANGED;
	}
}
