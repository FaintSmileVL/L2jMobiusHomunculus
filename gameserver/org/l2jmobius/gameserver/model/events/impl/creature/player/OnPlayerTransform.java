package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerTransform implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _transformId;
	
	public OnPlayerTransform(PlayerInstance player, int transformId)
	{
		_player = player;
		_transformId = transformId;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getTransformId()
	{
		return _transformId;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_TRANSFORM;
	}
}
