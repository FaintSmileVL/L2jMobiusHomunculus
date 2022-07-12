package org.l2jmobius.gameserver.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.effects.AbstractEffect;
import org.l2jmobius.gameserver.scripting.ScriptEngineManager;

/**
 * @author BiggBoss, UnAfraid
 */
public class EffectHandler
{
	private final Map<String, Function<StatSet, AbstractEffect>> _effectHandlerFactories = new HashMap<>();
	
	public void registerHandler(String name, Function<StatSet, AbstractEffect> handlerFactory)
	{
		_effectHandlerFactories.put(name, handlerFactory);
	}
	
	public Function<StatSet, AbstractEffect> getHandlerFactory(String name)
	{
		return _effectHandlerFactories.get(name);
	}
	
	public int size()
	{
		return _effectHandlerFactories.size();
	}
	
	public void executeScript()
	{
		try
		{
			ScriptEngineManager.getInstance().executeScript(ScriptEngineManager.EFFECT_MASTER_HANDLER_FILE);
		}
		catch (Exception e)
		{
			throw new Error("Problems while running EffectMansterHandler", e);
		}
	}
	
	private static final class SingletonHolder
	{
		protected static final EffectHandler INSTANCE = new EffectHandler();
	}
	
	public static EffectHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
}
