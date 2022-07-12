package org.l2jmobius.gameserver.handler;

import java.util.EnumMap;
import java.util.Map;

import org.l2jmobius.gameserver.enums.InstanceType;

/**
 * @author UnAfraid
 */
public class ActionHandler implements IHandler<IActionHandler, InstanceType>
{
	private final Map<InstanceType, IActionHandler> _actions;
	
	public static ActionHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	protected ActionHandler()
	{
		_actions = new EnumMap<>(InstanceType.class);
	}
	
	@Override
	public void registerHandler(IActionHandler handler)
	{
		_actions.put(handler.getInstanceType(), handler);
	}
	
	@Override
	public synchronized void removeHandler(IActionHandler handler)
	{
		_actions.remove(handler.getInstanceType());
	}
	
	@Override
	public IActionHandler getHandler(InstanceType iType)
	{
		IActionHandler result = null;
		for (InstanceType t = iType; t != null; t = t.getParent())
		{
			result = _actions.get(t);
			if (result != null)
			{
				break;
			}
		}
		return result;
	}
	
	@Override
	public int size()
	{
		return _actions.size();
	}
	
	private static class SingletonHolder
	{
		protected static final ActionHandler INSTANCE = new ActionHandler();
	}
}