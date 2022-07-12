package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.data.IXmlReader;

/**
 * This class holds the Experience points for each level for players and pets.
 * @author mrTJO
 */
public class ExperienceData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(ExperienceData.class.getName());
	
	private final Map<Integer, Long> _expTable = new HashMap<>();
	private final Map<Integer, Double> _traningRateTable = new HashMap<>();
	
	private int MAX_LEVEL;
	private int MAX_PET_LEVEL;
	
	/**
	 * Instantiates a new experience table.
	 */
	protected ExperienceData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_expTable.clear();
		_traningRateTable.clear();
		parseDatapackFile("data/stats/experience.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _expTable.size() + " levels.");
		LOGGER.info(getClass().getSimpleName() + ": Max Player Level is " + (MAX_LEVEL - 1) + ".");
		LOGGER.info(getClass().getSimpleName() + ": Max Pet Level is " + (MAX_PET_LEVEL - 1) + ".");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		final Node table = doc.getFirstChild();
		final NamedNodeMap tableAttr = table.getAttributes();
		MAX_LEVEL = Integer.parseInt(tableAttr.getNamedItem("maxLevel").getNodeValue()) + 1;
		MAX_PET_LEVEL = Integer.parseInt(tableAttr.getNamedItem("maxPetLevel").getNodeValue()) + 1;
		if (MAX_LEVEL > Config.PLAYER_MAXIMUM_LEVEL)
		{
			MAX_LEVEL = Config.PLAYER_MAXIMUM_LEVEL;
		}
		if (MAX_PET_LEVEL > MAX_LEVEL)
		{
			MAX_PET_LEVEL = MAX_LEVEL; // Pet level should not exceed owner level.
		}
		
		int maxLevel = 0;
		for (Node n = table.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("experience".equals(n.getNodeName()))
			{
				final NamedNodeMap attrs = n.getAttributes();
				maxLevel = parseInteger(attrs, "level");
				if (maxLevel > Config.PLAYER_MAXIMUM_LEVEL)
				{
					break;
				}
				_expTable.put(maxLevel, parseLong(attrs, "tolevel"));
				_traningRateTable.put(maxLevel, parseDouble(attrs, "trainingRate"));
			}
		}
	}
	
	/**
	 * Gets the exp for level.
	 * @param level the level required.
	 * @return the experience points required to reach the given level.
	 */
	public long getExpForLevel(int level)
	{
		if (level > Config.PLAYER_MAXIMUM_LEVEL)
		{
			return _expTable.get(Config.PLAYER_MAXIMUM_LEVEL);
		}
		return _expTable.get(level);
	}
	
	public double getTrainingRate(int level)
	{
		if (level > Config.PLAYER_MAXIMUM_LEVEL)
		{
			return _traningRateTable.get(Config.PLAYER_MAXIMUM_LEVEL);
		}
		return _traningRateTable.get(level);
	}
	
	/**
	 * Gets the max level.
	 * @return the maximum level acquirable by a player.
	 */
	public int getMaxLevel()
	{
		return MAX_LEVEL;
	}
	
	/**
	 * Gets the max pet level.
	 * @return the maximum level acquirable by a pet.
	 */
	public int getMaxPetLevel()
	{
		return MAX_PET_LEVEL;
	}
	
	/**
	 * Gets the single instance of ExperienceTable.
	 * @return single instance of ExperienceTable
	 */
	public static ExperienceData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ExperienceData INSTANCE = new ExperienceData();
	}
}
