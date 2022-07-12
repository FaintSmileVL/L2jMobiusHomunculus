package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author St3eT
 */
public class OnPlayerSocialAction implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _socialActionId;
	
	public OnPlayerSocialAction(PlayerInstance player, int socialActionId)
	{
		_player = player;
		_socialActionId = socialActionId;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getSocialActionId()
	{
		return _socialActionId;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_SOCIAL_ACTION;
	}
}