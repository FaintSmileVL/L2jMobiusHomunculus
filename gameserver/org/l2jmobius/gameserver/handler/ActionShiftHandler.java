package org.l2jmobius.gameserver.handler;

import java.util.EnumMap;
import java.util.Map;

import org.l2jmobius.gameserver.enums.InstanceType;

/**
 * @author UnAfraid
 */
public class ActionShiftHandler implements IHandler<IActionShiftHandler, InstanceType>
{
	private final Map<InstanceType, IActionShiftHandler> _actionsShift;
	
	protected ActionShiftHandler()
	{
		_actionsShift = new EnumMap<>(InstanceType.class);
	}
	
	@Override
	public void registerHandler(IActionShiftHandler handler)
	{
		_actionsShift.put(handler.getInstanceType(), handler);
	}
	
	@Override
	public synchronized void removeHandler(IActionShiftHandler handler)
	{
		_actionsShift.remove(handler.getInstanceType());
	}
	
	@Override
	public IActionShiftHandler getHandler(InstanceType iType)
	{
		IActionShiftHandler result = null;
		for (InstanceType t = iType; t != null; t = t.getParent())
		{
			result = _actionsShift.get(t);
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
		return _actionsShift.size();
	}
	
	public static ActionShiftHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ActionShiftHandler INSTANCE = new ActionShiftHandler();
	}
}