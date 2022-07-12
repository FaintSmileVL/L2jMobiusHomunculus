package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.enums.QuestType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerQuestComplete implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _questId;
	private final QuestType _questType;
	
	public OnPlayerQuestComplete(PlayerInstance player, int questId, QuestType questType)
	{
		_player = player;
		_questId = questId;
		_questType = questType;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getQuestId()
	{
		return _questId;
	}
	
	public QuestType getQuestType()
	{
		return _questType;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_QUEST_COMPLETE;
	}
}
