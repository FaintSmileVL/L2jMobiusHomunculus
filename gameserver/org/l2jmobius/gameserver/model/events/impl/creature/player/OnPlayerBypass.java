package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerBypass implements IBaseEvent
{
	private final PlayerInstance _player;
	private final String _command;
	
	public OnPlayerBypass(PlayerInstance player, String command)
	{
		_player = player;
		_command = command;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public String getCommand()
	{
		return _command;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_BYPASS;
	}
}
