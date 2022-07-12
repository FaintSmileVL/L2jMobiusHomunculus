package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.Henna;

/**
 * @author UnAfraid
 */
public class OnPlayerHennaRemove implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Henna _henna;
	
	public OnPlayerHennaRemove(PlayerInstance player, Henna henna)
	{
		_player = player;
		_henna = henna;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public Henna getHenna()
	{
		return _henna;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_HENNA_REMOVE;
	}
}
