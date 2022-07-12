package org.l2jmobius.gameserver.handler;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.skills.targets.AffectObject;

/**
 * @author Nik
 */
public class AffectObjectHandler implements IHandler<IAffectObjectHandler, Enum<AffectObject>>
{
	private final Map<Enum<AffectObject>, IAffectObjectHandler> _datatable;
	
	protected AffectObjectHandler()
	{
		_datatable = new HashMap<>();
	}
	
	@Override
	public void registerHandler(IAffectObjectHandler handler)
	{
		_datatable.put(handler.getAffectObjectType(), handler);
	}
	
	@Override
	public synchronized void removeHandler(IAffectObjectHandler handler)
	{
		_datatable.remove(handler.getAffectObjectType());
	}
	
	@Override
	public IAffectObjectHandler getHandler(Enum<AffectObject> targetType)
	{
		return _datatable.get(targetType);
	}
	
	@Override
	public int size()
	{
		return _datatable.size();
	}
	
	public static AffectObjectHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AffectObjectHandler INSTANCE = new AffectObjectHandler();
	}
}
