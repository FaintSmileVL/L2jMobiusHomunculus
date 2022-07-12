package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerLogin implements IBaseEvent
{
	private final PlayerInstance _player;
	
	public OnPlayerLogin(PlayerInstance player)
	{
		_player = player;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_LOGIN;
	}
}
