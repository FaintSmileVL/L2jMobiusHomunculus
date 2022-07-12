package org.l2jmobius.gameserver.model.eventengine;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author UnAfraid
 */
public class EventMethodNotification
{
	private final AbstractEventManager<?> _manager;
	private final Method _method;
	private final Object[] _args;
	
	/**
	 * @param manager
	 * @param methodName
	 * @param args
	 * @throws NoSuchMethodException
	 */
	public EventMethodNotification(AbstractEventManager<?> manager, String methodName, List<Object> args) throws NoSuchMethodException
	{
		_manager = manager;
		_method = manager.getClass().getDeclaredMethod(methodName, args.stream().map(Object::getClass).toArray(Class[]::new));
		_args = args.toArray();
	}
	
	public AbstractEventManager<?> getManager()
	{
		return _manager;
	}
	
	public Method getMethod()
	{
		return _method;
	}
	
	public void execute() throws Exception
	{
		if (Modifier.isStatic(_method.getModifiers()))
		{
			invoke(null);
		}
		else
		{
			// Attempt to find getInstance() method
			for (Method method : _manager.getClass().getMethods())
			{
				if (Modifier.isStatic(method.getModifiers()) && (_manager.getClass().isAssignableFrom(method.getReturnType())) && (method.getParameterCount() == 0))
				{
					final Object instance = method.invoke(null);
					invoke(instance);
				}
			}
		}
	}
	
	private void invoke(Object instance) throws Exception
	{
		// Java 1.8
		// final boolean wasAccessible = _method.isAccessible();
		// Java 10
		final boolean wasAccessible = _method.canAccess(instance);
		if (!wasAccessible)
		{
			_method.setAccessible(true);
		}
		_method.invoke(instance, _args);
		_method.setAccessible(wasAccessible);
	}
}
