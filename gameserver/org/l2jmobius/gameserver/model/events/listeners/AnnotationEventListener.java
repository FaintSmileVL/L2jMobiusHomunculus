package org.l2jmobius.gameserver.model.events.listeners;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.ListenersContainer;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.events.returns.AbstractEventReturn;

/**
 * Annotation event listener provides dynamically attached callback to any method operation with or without any return object.
 * @author UnAfraid
 */
public class AnnotationEventListener extends AbstractEventListener
{
	private static final Logger LOGGER = Logger.getLogger(AnnotationEventListener.class.getName());
	private final Method _callback;
	
	public AnnotationEventListener(ListenersContainer container, EventType type, Method callback, Object owner, int priority)
	{
		super(container, type, owner);
		_callback = callback;
		setPriority(priority);
	}
	
	@Override
	public <R extends AbstractEventReturn> R executeEvent(IBaseEvent event, Class<R> returnBackClass)
	{
		try
		{
			final Object result = _callback.invoke(getOwner(), event);
			if (_callback.getReturnType() == returnBackClass)
			{
				return returnBackClass.cast(result);
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Error while invoking " + _callback.getName() + " on " + getOwner(), e);
		}
		return null;
	}
}
