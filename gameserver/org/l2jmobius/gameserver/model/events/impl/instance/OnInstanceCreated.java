package org.l2jmobius.gameserver.model.events.impl.instance;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.instancezone.Instance;

/**
 * @author malyelfik
 */
public class OnInstanceCreated implements IBaseEvent
{
	private final Instance _instance;
	private final PlayerInstance _creator;
	
	public OnInstanceCreated(Instance instance, PlayerInstance creator)
	{
		_instance = instance;
		_creator = creator;
	}
	
	public Instance getInstanceWorld()
	{
		return _instance;
	}
	
	public PlayerInstance getCreator()
	{
		return _creator;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_INSTANCE_CREATED;
	}
}