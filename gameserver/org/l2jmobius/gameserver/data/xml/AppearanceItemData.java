package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.enums.Race;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.holders.AppearanceHolder;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceStone;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceTargetType;
import org.l2jmobius.gameserver.model.items.type.CrystalType;

/**
 * @author UnAfraid
 */
public class AppearanceItemData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(AppearanceItemData.class.getName());
	
	private AppearanceStone[] _stones;
	private final Map<Integer, AppearanceStone> _stoneMap = new HashMap<>();
	
	protected AppearanceItemData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		parseDatapackFile("data/AppearanceStones.xml");
		
		_stones = new AppearanceStone[Collections.max(_stoneMap.keySet()) + 1];
		for (Entry<Integer, AppearanceStone> stone : _stoneMap.entrySet())
		{
			_stones[stone.getKey()] = stone.getValue();
		}
		
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _stoneMap.size() + " stones.");
		
		//@formatter:off
		/*
		for (Item item : ItemTable.getInstance().getAllItems())
		{
			if ((item == null) || !item.getName().contains("Appearance Stone"))
			{
				continue;
			}
			if (item.getName().contains("Pack") || _stones.containsKey(item.getId()))
			{
				continue;
			}
			
			System.out.println("Unhandled appearance stone: " + item);
		}
		*/
		//@formatter:on
		
		_stoneMap.clear();
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("appearance_stone".equalsIgnoreCase(d.getNodeName()))
					{
						final AppearanceStone stone = new AppearanceStone(new StatSet(parseAttributes(d)));
						for (Node c = d.getFirstChild(); c != null; c = c.getNextSibling())
						{
							switch (c.getNodeName())
							{
								case "grade":
								{
									final CrystalType type = CrystalType.valueOf(c.getTextContent());
									stone.addCrystalType(type);
									break;
								}
								case "targetType":
								{
									final AppearanceTargetType type = AppearanceTargetType.valueOf(c.getTextContent());
									stone.addTargetType(type);
									break;
								}
								case "bodyPart":
								{
									final long part = ItemTable.SLOTS.get(c.getTextContent());
									stone.addBodyPart(part);
									break;
								}
								case "race":
								{
									final Race race = Race.valueOf(c.getTextContent());
									stone.addRace(race);
									break;
								}
								case "raceNot":
								{
									final Race raceNot = Race.valueOf(c.getTextContent());
									stone.addRaceNot(raceNot);
									break;
								}
								case "visual":
								{
									stone.addVisualId(new AppearanceHolder(new StatSet(parseAttributes(c))));
								}
							}
						}
						if (ItemTable.getInstance().getTemplate(stone.getId()) != null)
						{
							_stoneMap.put(stone.getId(), stone);
						}
						else
						{
							LOGGER.info(getClass().getSimpleName() + ": Could not find appearance stone item " + stone.getId());
						}
					}
				}
			}
		}
	}
	
	public AppearanceStone getStone(int stone)
	{
		if (_stones.length > stone)
		{
			return _stones[stone];
		}
		return null;
	}
	
	/**
	 * Gets the single instance of AppearanceItemData.
	 * @return single instance of AppearanceItemData
	 */
	public static AppearanceItemData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AppearanceItemData INSTANCE = new AppearanceItemData();
	}
}
