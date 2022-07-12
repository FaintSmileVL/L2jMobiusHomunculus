package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerDlgAnswer implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _messageId;
	private final int _answer;
	private final int _requesterId;
	
	public OnPlayerDlgAnswer(PlayerInstance player, int messageId, int answer, int requesterId)
	{
		_player = player;
		_messageId = messageId;
		_answer = answer;
		_requesterId = requesterId;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getMessageId()
	{
		return _messageId;
	}
	
	public int getAnswer()
	{
		return _answer;
	}
	
	public int getRequesterId()
	{
		return _requesterId;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_DLG_ANSWER;
	}
}
