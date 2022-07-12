package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerChat implements IBaseEvent
{
	private final PlayerInstance _player;
	private final String _target;
	private final String _text;
	private final ChatType _type;
	
	public OnPlayerChat(PlayerInstance player, String target, String text, ChatType type)
	{
		_player = player;
		_target = target;
		_text = text;
		_type = type;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public String getTarget()
	{
		return _target;
	}
	
	public String getText()
	{
		return _text;
	}
	
	public ChatType getChatType()
	{
		return _type;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_CHAT;
	}
}
