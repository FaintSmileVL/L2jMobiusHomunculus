package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author St3eT
 */
public class OnPlayerPressTutorialMark implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _markId;
	
	public OnPlayerPressTutorialMark(PlayerInstance player, int markId)
	{
		_player = player;
		_markId = markId;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getMarkId()
	{
		return _markId;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_PRESS_TUTORIAL_MARK;
	}
}
