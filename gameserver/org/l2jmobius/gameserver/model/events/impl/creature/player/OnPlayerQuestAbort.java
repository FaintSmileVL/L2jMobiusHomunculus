package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author Sdw
 */
public class OnPlayerQuestAbort implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _questId;
	
	public OnPlayerQuestAbort(PlayerInstance player, int questId)
	{
		_player = player;
		_questId = questId;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getQuestId()
	{
		return _questId;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_QUEST_ABORT;
	}
}
