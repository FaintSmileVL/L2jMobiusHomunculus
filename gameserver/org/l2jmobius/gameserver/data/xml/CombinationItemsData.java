package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.items.combination.CombinationItem;
import org.l2jmobius.gameserver.model.items.combination.CombinationItemReward;
import org.l2jmobius.gameserver.model.items.combination.CombinationItemType;

/**
 * @author UnAfraid
 */
public class CombinationItemsData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(CombinationItemsData.class.getName());
	private final List<CombinationItem> _items = new ArrayList<>();
	
	protected CombinationItemsData()
	{
		load();
	}
	
	@Override
	public synchronized void load()
	{
		_items.clear();
		parseDatapackFile("data/CombinationItems.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _items.size() + " combinations.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "item", itemNode ->
		{
			final CombinationItem item = new CombinationItem(new StatSet(parseAttributes(itemNode)));
			
			forEach(itemNode, "reward", rewardNode ->
			{
				final int id = parseInteger(rewardNode.getAttributes(), "id");
				final int count = parseInteger(rewardNode.getAttributes(), "count", 1);
				final CombinationItemType type = parseEnum(rewardNode.getAttributes(), CombinationItemType.class, "type");
				item.addReward(new CombinationItemReward(id, count, type));
				if (ItemTable.getInstance().getTemplate(id) == null)
				{
					LOGGER.info(getClass().getSimpleName() + ": Could not find item with id " + id);
				}
			});
			_items.add(item);
		}));
	}
	
	public int getLoadedElementsCount()
	{
		return _items.size();
	}
	
	public List<CombinationItem> getItems()
	{
		return _items;
	}
	
	public CombinationItem getItemsBySlots(int firstSlot, int secondSlot)
	{
		for (CombinationItem item : _items)
		{
			if ((item.getItemOne() == firstSlot) && (item.getItemTwo() == secondSlot))
			{
				return item;
			}
		}
		return null;
	}
	
	public List<CombinationItem> getItemsByFirstSlot(int id)
	{
		final List<CombinationItem> result = new ArrayList<>();
		for (CombinationItem item : _items)
		{
			if (item.getItemOne() == id)
			{
				result.add(item);
			}
		}
		return result;
	}
	
	public List<CombinationItem> getItemsBySecondSlot(int id)
	{
		final List<CombinationItem> result = new ArrayList<>();
		for (CombinationItem item : _items)
		{
			if (item.getItemTwo() == id)
			{
				result.add(item);
			}
		}
		return result;
	}
	
	public static final CombinationItemsData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final CombinationItemsData INSTANCE = new CombinationItemsData();
	}
}