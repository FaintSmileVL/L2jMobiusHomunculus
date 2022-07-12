package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.Mentee;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerMenteeRemove implements IBaseEvent
{
	private final PlayerInstance _mentor;
	private final Mentee _mentee;
	
	public OnPlayerMenteeRemove(PlayerInstance mentor, Mentee mentee)
	{
		_mentor = mentor;
		_mentee = mentee;
	}
	
	public PlayerInstance getMentor()
	{
		return _mentor;
	}
	
	public Mentee getMentee()
	{
		return _mentee;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_MENTEE_REMOVE;
	}
}
