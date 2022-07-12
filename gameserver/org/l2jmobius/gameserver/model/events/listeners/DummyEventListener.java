package org.l2jmobius.gameserver.model.events.listeners;

import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.ListenersContainer;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.events.returns.AbstractEventReturn;

/**
 * Runnable event listener provides callback operation without any parameters and return object.
 * @author UnAfraid
 */
public class DummyEventListener extends AbstractEventListener
{
	public DummyEventListener(ListenersContainer container, EventType type, Object owner)
	{
		super(container, type, owner);
	}
	
	@Override
	public <R extends AbstractEventReturn> R executeEvent(IBaseEvent event, Class<R> returnBackClass)
	{
		return null;
	}
}
