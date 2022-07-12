package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.templates.PlayerTemplate;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerProfessionChange implements IBaseEvent
{
	private final PlayerInstance _player;
	private final PlayerTemplate _template;
	private final boolean _isSubClass;
	
	public OnPlayerProfessionChange(PlayerInstance player, PlayerTemplate template, boolean isSubClass)
	{
		_player = player;
		_template = template;
		_isSubClass = isSubClass;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public PlayerTemplate getTemplate()
	{
		return _template;
	}
	
	public boolean isSubClass()
	{
		return _isSubClass;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_PROFESSION_CHANGE;
	}
}
