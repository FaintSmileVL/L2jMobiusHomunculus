package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerMoveRequest implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Location _location;
	
	public OnPlayerMoveRequest(PlayerInstance player, Location loc)
	{
		_player = player;
		_location = loc;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public Location getLocation()
	{
		return _location;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_MOVE_REQUEST;
	}
}
