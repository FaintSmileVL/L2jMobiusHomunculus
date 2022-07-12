package org.l2jmobius.gameserver.handler;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.skills.targets.AffectScope;

/**
 * @author Nik
 */
public class AffectScopeHandler implements IHandler<IAffectScopeHandler, Enum<AffectScope>>
{
	private final Map<Enum<AffectScope>, IAffectScopeHandler> _datatable;
	
	protected AffectScopeHandler()
	{
		_datatable = new HashMap<>();
	}
	
	@Override
	public void registerHandler(IAffectScopeHandler handler)
	{
		_datatable.put(handler.getAffectScopeType(), handler);
	}
	
	@Override
	public synchronized void removeHandler(IAffectScopeHandler handler)
	{
		_datatable.remove(handler.getAffectScopeType());
	}
	
	@Override
	public IAffectScopeHandler getHandler(Enum<AffectScope> affectScope)
	{
		return _datatable.get(affectScope);
	}
	
	@Override
	public int size()
	{
		return _datatable.size();
	}
	
	public static AffectScopeHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AffectScopeHandler INSTANCE = new AffectScopeHandler();
	}
}
