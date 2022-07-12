package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author malyelfik
 */
public class OnPlayerSubChange implements IBaseEvent
{
	private final PlayerInstance _player;
	
	public OnPlayerSubChange(PlayerInstance player)
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
		return EventType.ON_PLAYER_SUB_CHANGE;
	}
}