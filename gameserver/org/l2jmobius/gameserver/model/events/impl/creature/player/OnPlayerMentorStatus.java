package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerMentorStatus implements IBaseEvent
{
	private final PlayerInstance _mentor;
	private final boolean _isOnline;
	
	public OnPlayerMentorStatus(PlayerInstance mentor, boolean isOnline)
	{
		_mentor = mentor;
		_isOnline = isOnline;
	}
	
	public PlayerInstance getMentor()
	{
		return _mentor;
	}
	
	public boolean isMentorOnline()
	{
		return _isOnline;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_MENTOR_STATUS;
	}
}
