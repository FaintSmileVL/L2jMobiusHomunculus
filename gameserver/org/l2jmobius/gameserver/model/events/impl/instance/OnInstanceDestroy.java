package org.l2jmobius.gameserver.model.events.impl.instance;

import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.instancezone.Instance;

/**
 * @author malyelfik
 */
public class OnInstanceDestroy implements IBaseEvent
{
	private final Instance _instance;
	
	public OnInstanceDestroy(Instance instance)
	{
		_instance = instance;
	}
	
	public Instance getInstanceWorld()
	{
		return _instance;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_INSTANCE_DESTROY;
	}
}