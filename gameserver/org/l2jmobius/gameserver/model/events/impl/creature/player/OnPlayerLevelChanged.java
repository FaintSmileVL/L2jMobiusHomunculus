package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerLevelChanged implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _oldLevel;
	private final int _newLevel;
	
	public OnPlayerLevelChanged(PlayerInstance player, int oldLevel, int newLevel)
	{
		_player = player;
		_oldLevel = oldLevel;
		_newLevel = newLevel;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getOldLevel()
	{
		return _oldLevel;
	}
	
	public int getNewLevel()
	{
		return _newLevel;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_LEVEL_CHANGED;
	}
}
