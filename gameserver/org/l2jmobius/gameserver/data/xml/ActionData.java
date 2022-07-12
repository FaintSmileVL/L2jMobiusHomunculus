package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.model.ActionDataHolder;
import org.l2jmobius.gameserver.model.StatSet;

/**
 * @author UnAfraid
 */
public class ActionData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(ActionData.class.getName());
	
	private final Map<Integer, ActionDataHolder> _actionData = new HashMap<>();
	private final Map<Integer, Integer> _actionSkillsData = new HashMap<>(); // skillId, actionId
	
	protected ActionData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_actionData.clear();
		_actionSkillsData.clear();
		parseDatapackFile("data/ActionData.xml");
		
		for (ActionDataHolder holder : _actionData.values())
		{
			if (holder.getHandler().equals("PetSkillUse") || holder.getHandler().equals("ServitorSkillUse"))
			{
				_actionSkillsData.put(holder.getOptionId(), holder.getId());
			}
		}
		
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _actionData.size() + " player actions.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "action", actionNode ->
		{
			final ActionDataHolder holder = new ActionDataHolder(new StatSet(parseAttributes(actionNode)));
			_actionData.put(holder.getId(), holder);
		}));
	}
	
	/**
	 * @param id
	 * @return the ActionDataHolder for specified id
	 */
	public ActionDataHolder getActionData(int id)
	{
		return _actionData.get(id);
	}
	
	/**
	 * @param skillId
	 * @return the actionId corresponding to the skillId or -1 if no actionId is found for the specified skill.
	 */
	public int getSkillActionId(int skillId)
	{
		return _actionSkillsData.getOrDefault(skillId, -1);
	}
	
	/**
	 * Gets the single instance of ActionData.
	 * @return single instance of ActionData
	 */
	public static ActionData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ActionData INSTANCE = new ActionData();
	}
}
