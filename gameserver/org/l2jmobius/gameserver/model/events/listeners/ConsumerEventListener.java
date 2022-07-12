package org.l2jmobius.gameserver.model.events.listeners;

import java.util.function.Consumer;

import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.ListenersContainer;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.events.returns.AbstractEventReturn;

/**
 * Consumer event listener provides callback operation without any return object.
 * @author UnAfraid
 */
public class ConsumerEventListener extends AbstractEventListener
{
	private final Consumer<IBaseEvent> _callback;
	
	@SuppressWarnings("unchecked")
	public ConsumerEventListener(ListenersContainer container, EventType type, Consumer<? extends IBaseEvent> callback, Object owner)
	{
		super(container, type, owner);
		_callback = (Consumer<IBaseEvent>) callback;
	}
	
	@Override
	public <R extends AbstractEventReturn> R executeEvent(IBaseEvent event, Class<R> returnBackClass)
	{
		_callback.accept(event);
		return null;
	}
}
