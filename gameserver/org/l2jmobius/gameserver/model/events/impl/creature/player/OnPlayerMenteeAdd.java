package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerMenteeAdd implements IBaseEvent
{
	private final PlayerInstance _mentor;
	private final PlayerInstance _mentee;
	
	public OnPlayerMenteeAdd(PlayerInstance mentor, PlayerInstance mentee)
	{
		_mentor = mentor;
		_mentee = mentee;
	}
	
	public PlayerInstance getMentor()
	{
		return _mentor;
	}
	
	public PlayerInstance getMentee()
	{
		return _mentee;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_MENTEE_ADD;
	}
}
