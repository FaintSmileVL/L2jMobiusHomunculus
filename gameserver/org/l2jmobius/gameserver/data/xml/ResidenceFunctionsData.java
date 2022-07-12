package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.residences.ResidenceFunctionTemplate;

/**
 * The residence functions data
 * @author UnAfraid
 */
public class ResidenceFunctionsData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(ResidenceFunctionsData.class.getName());
	private final Map<Integer, List<ResidenceFunctionTemplate>> _functions = new HashMap<>();
	
	protected ResidenceFunctionsData()
	{
		load();
	}
	
	@Override
	public synchronized void load()
	{
		_functions.clear();
		parseDatapackFile("data/ResidenceFunctions.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _functions.size() + " functions.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", list -> forEach(list, "function", func ->
		{
			final NamedNodeMap attrs = func.getAttributes();
			final StatSet set = new StatSet(HashMap::new);
			for (int i = 0; i < attrs.getLength(); i++)
			{
				final Node node = attrs.item(i);
				set.set(node.getNodeName(), node.getNodeValue());
			}
			forEach(func, "function", levelNode ->
			{
				final NamedNodeMap levelAttrs = levelNode.getAttributes();
				final StatSet levelSet = new StatSet(HashMap::new);
				levelSet.merge(set);
				for (int i = 0; i < levelAttrs.getLength(); i++)
				{
					final Node node = levelAttrs.item(i);
					levelSet.set(node.getNodeName(), node.getNodeValue());
				}
				final ResidenceFunctionTemplate template = new ResidenceFunctionTemplate(levelSet);
				_functions.computeIfAbsent(template.getId(), key -> new ArrayList<>()).add(template);
			});
		}));
	}
	
	/**
	 * @param id
	 * @param level
	 * @return function template by id and level, null if not available
	 */
	public ResidenceFunctionTemplate getFunction(int id, int level)
	{
		if (_functions.containsKey(id))
		{
			for (ResidenceFunctionTemplate template : _functions.get(id))
			{
				if (template.getLevel() == level)
				{
					return template;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param id
	 * @return function template by id, null if not available
	 */
	public List<ResidenceFunctionTemplate> getFunctions(int id)
	{
		return _functions.get(id);
	}
	
	public static ResidenceFunctionsData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ResidenceFunctionsData INSTANCE = new ResidenceFunctionsData();
	}
}
