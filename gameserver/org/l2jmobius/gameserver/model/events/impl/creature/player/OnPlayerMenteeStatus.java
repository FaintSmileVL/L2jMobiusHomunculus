package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerMenteeStatus implements IBaseEvent
{
	private final PlayerInstance _mentee;
	private final boolean _isOnline;
	
	public OnPlayerMenteeStatus(PlayerInstance mentee, boolean isOnline)
	{
		_mentee = mentee;
		_isOnline = isOnline;
	}
	
	public PlayerInstance getMentee()
	{
		return _mentee;
	}
	
	public boolean isMenteeOnline()
	{
		return _isOnline;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_MENTEE_STATUS;
	}
}
