package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.enums.TeleportType;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.teleporter.TeleportHolder;

/**
 * @author UnAfraid
 */
public class TeleporterData implements IXmlReader
{
	// Logger instance
	private static final Logger LOGGER = Logger.getLogger(TeleporterData.class.getName());
	// Teleporter data
	private final Map<Integer, Map<String, TeleportHolder>> _teleporters = new ConcurrentHashMap<>();
	
	protected TeleporterData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_teleporters.clear();
		parseDatapackDirectory("data/teleporters", true);
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _teleporters.size() + " npc teleporters.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", list -> forEach(list, "npc", npc ->
		{
			final Map<String, TeleportHolder> teleList = new HashMap<>();
			// Parse npc node child
			final int npcId = parseInteger(npc.getAttributes(), "id");
			forEach(npc, node ->
			{
				switch (node.getNodeName())
				{
					case "teleport":
					{
						final NamedNodeMap nodeAttrs = node.getAttributes();
						// Parse attributes
						final TeleportType type = parseEnum(nodeAttrs, TeleportType.class, "type");
						final String name = parseString(nodeAttrs, "name", type.name());
						// Parse locations
						final TeleportHolder holder = new TeleportHolder(name, type);
						forEach(node, "location", location -> holder.registerLocation(new StatSet(parseAttributes(location))));
						// Register holder
						if (teleList.putIfAbsent(name, holder) != null)
						{
							LOGGER.warning("Duplicate teleport list (" + name + ") has been found for NPC: " + npcId);
						}
						break;
					}
					case "npcs":
					{
						forEach(node, "npc", npcNode ->
						{
							final int id = parseInteger(npcNode.getAttributes(), "id");
							registerTeleportList(id, teleList);
						});
						break;
					}
				}
			});
			registerTeleportList(npcId, teleList);
		}));
	}
	
	public int getTeleporterCount()
	{
		return _teleporters.size();
	}
	
	/**
	 * Register teleport data to global teleport list holder. Also show warning when any duplicate occurs.
	 * @param npcId template id of teleporter
	 * @param teleList teleport data to register
	 */
	private void registerTeleportList(int npcId, Map<String, TeleportHolder> teleList)
	{
		_teleporters.put(npcId, teleList);
	}
	
	/**
	 * Gets teleport data for specified NPC and list name
	 * @param npcId template id of teleporter
	 * @param listName name of teleport list
	 * @return {@link TeleportHolder} if found otherwise {@code null}
	 */
	public TeleportHolder getHolder(int npcId, String listName)
	{
		return _teleporters.getOrDefault(npcId, Collections.emptyMap()).get(listName);
	}
	
	/**
	 * Gets the single instance of TeleportersData.
	 * @return single instance of TeleportersData
	 */
	public static TeleporterData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final TeleporterData INSTANCE = new TeleporterData();
	}
}
