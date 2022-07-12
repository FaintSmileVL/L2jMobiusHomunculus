package org.l2jmobius.gameserver.handler;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.skills.targets.TargetType;

/**
 * @author UnAfraid
 */
public class TargetHandler implements IHandler<ITargetTypeHandler, Enum<TargetType>>
{
	private final Map<Enum<TargetType>, ITargetTypeHandler> _datatable;
	
	protected TargetHandler()
	{
		_datatable = new HashMap<>();
	}
	
	@Override
	public void registerHandler(ITargetTypeHandler handler)
	{
		_datatable.put(handler.getTargetType(), handler);
	}
	
	@Override
	public synchronized void removeHandler(ITargetTypeHandler handler)
	{
		_datatable.remove(handler.getTargetType());
	}
	
	@Override
	public ITargetTypeHandler getHandler(Enum<TargetType> targetType)
	{
		return _datatable.get(targetType);
	}
	
	@Override
	public int size()
	{
		return _datatable.size();
	}
	
	public static TargetHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final TargetHandler INSTANCE = new TargetHandler();
	}
}
