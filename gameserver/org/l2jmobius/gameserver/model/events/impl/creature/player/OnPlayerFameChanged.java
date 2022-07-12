package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerFameChanged implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _oldFame;
	private final int _newFame;
	
	public OnPlayerFameChanged(PlayerInstance player, int oldFame, int newFame)
	{
		_player = player;
		_oldFame = oldFame;
		_newFame = newFame;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getOldFame()
	{
		return _oldFame;
	}
	
	public int getNewFame()
	{
		return _newFame;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_FAME_CHANGED;
	}
}
