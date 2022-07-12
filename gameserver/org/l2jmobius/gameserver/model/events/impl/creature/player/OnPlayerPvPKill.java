package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerPvPKill implements IBaseEvent
{
	private final PlayerInstance _player;
	private final PlayerInstance _target;
	
	public OnPlayerPvPKill(PlayerInstance player, PlayerInstance target)
	{
		_player = player;
		_target = target;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public PlayerInstance getTarget()
	{
		return _target;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_PVP_KILL;
	}
}
