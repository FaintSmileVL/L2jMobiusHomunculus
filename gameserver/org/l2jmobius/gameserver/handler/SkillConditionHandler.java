package org.l2jmobius.gameserver.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.skills.ISkillCondition;
import org.l2jmobius.gameserver.scripting.ScriptEngineManager;

/**
 * @author NosBit
 */
public class SkillConditionHandler
{
	private final Map<String, Function<StatSet, ISkillCondition>> _skillConditionHandlerFactories = new HashMap<>();
	
	public void registerHandler(String name, Function<StatSet, ISkillCondition> handlerFactory)
	{
		_skillConditionHandlerFactories.put(name, handlerFactory);
	}
	
	public Function<StatSet, ISkillCondition> getHandlerFactory(String name)
	{
		return _skillConditionHandlerFactories.get(name);
	}
	
	public int size()
	{
		return _skillConditionHandlerFactories.size();
	}
	
	public void executeScript()
	{
		try
		{
			ScriptEngineManager.getInstance().executeScript(ScriptEngineManager.SKILL_CONDITION_HANDLER_FILE);
		}
		catch (Exception e)
		{
			throw new Error("Problems while running SkillMasterHandler", e);
		}
	}
	
	private static final class SingletonHolder
	{
		protected static final SkillConditionHandler INSTANCE = new SkillConditionHandler();
	}
	
	public static SkillConditionHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
}
