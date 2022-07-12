package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.network.serverpackets.fishing.ExFishingEnd.FishingEndReason;

/**
 * @author UnAfraid
 */
public class OnPlayerFishing implements IBaseEvent
{
	private final PlayerInstance _player;
	private final FishingEndReason _reason;
	
	public OnPlayerFishing(PlayerInstance player, FishingEndReason reason)
	{
		_player = player;
		_reason = reason;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public FishingEndReason getReason()
	{
		return _reason;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_FISHING;
	}
}
