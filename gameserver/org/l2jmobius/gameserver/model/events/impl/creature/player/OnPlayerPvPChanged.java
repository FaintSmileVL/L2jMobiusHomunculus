package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerPvPChanged implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _oldPoints;
	private final int _newPoints;
	
	public OnPlayerPvPChanged(PlayerInstance player, int oldPoints, int newPoints)
	{
		_player = player;
		_oldPoints = oldPoints;
		_newPoints = newPoints;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getOldPoints()
	{
		return _oldPoints;
	}
	
	public int getNewPoints()
	{
		return _newPoints;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_PVP_CHANGED;
	}
}
