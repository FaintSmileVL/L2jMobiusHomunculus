package org.l2jmobius.gameserver.model.events.impl.instance;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.instancezone.Instance;

/**
 * @author malyelfik
 */
public class OnInstanceEnter implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Instance _instance;
	
	public OnInstanceEnter(PlayerInstance player, Instance instance)
	{
		_player = player;
		_instance = instance;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public Instance getInstanceWorld()
	{
		return _instance;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_INSTANCE_ENTER;
	}
}