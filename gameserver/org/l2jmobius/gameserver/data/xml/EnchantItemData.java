package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.items.enchant.EnchantScroll;
import org.l2jmobius.gameserver.model.items.enchant.EnchantSupportItem;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * Loads item enchant data.
 * @author UnAfraid
 */
public class EnchantItemData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(EnchantItemData.class.getName());
	
	private final Map<Integer, EnchantScroll> _scrolls = new HashMap<>();
	private final Map<Integer, EnchantSupportItem> _supports = new HashMap<>();
	
	/**
	 * Instantiates a new enchant item data.
	 */
	public EnchantItemData()
	{
		load();
	}
	
	@Override
	public synchronized void load()
	{
		_scrolls.clear();
		_supports.clear();
		parseDatapackFile("data/EnchantItemData.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _scrolls.size() + " enchant scrolls.");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _supports.size() + " support items.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		StatSet set;
		Node att;
		NamedNodeMap attrs;
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("enchant".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						set = new StatSet();
						for (int i = 0; i < attrs.getLength(); i++)
						{
							att = attrs.item(i);
							set.set(att.getNodeName(), att.getNodeValue());
						}
						
						try
						{
							final EnchantScroll item = new EnchantScroll(set);
							for (Node cd = d.getFirstChild(); cd != null; cd = cd.getNextSibling())
							{
								if ("item".equalsIgnoreCase(cd.getNodeName()))
								{
									item.addItem(parseInteger(cd.getAttributes(), "id"));
								}
							}
							_scrolls.put(item.getId(), item);
						}
						catch (NullPointerException e)
						{
							LOGGER.warning(getClass().getSimpleName() + ": Unexistent enchant scroll: " + set.getString("id") + " defined in enchant data!");
						}
						catch (IllegalAccessError e)
						{
							LOGGER.warning(getClass().getSimpleName() + ": Wrong enchant scroll item type: " + set.getString("id") + " defined in enchant data!");
						}
					}
					else if ("support".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						set = new StatSet();
						for (int i = 0; i < attrs.getLength(); i++)
						{
							att = attrs.item(i);
							set.set(att.getNodeName(), att.getNodeValue());
						}
						
						try
						{
							final EnchantSupportItem item = new EnchantSupportItem(set);
							_supports.put(item.getId(), item);
						}
						catch (NullPointerException e)
						{
							LOGGER.warning(getClass().getSimpleName() + ": Unexistent enchant support item: " + set.getString("id") + " defined in enchant data!");
						}
						catch (IllegalAccessError e)
						{
							LOGGER.warning(getClass().getSimpleName() + ": Wrong enchant support item type: " + set.getString("id") + " defined in enchant data!");
						}
					}
				}
			}
		}
	}
	
	public Collection<EnchantScroll> getScrolls()
	{
		return _scrolls.values();
	}
	
	/**
	 * Gets the enchant scroll.
	 * @param scroll the scroll
	 * @return enchant template for scroll
	 */
	public EnchantScroll getEnchantScroll(ItemInstance scroll)
	{
		return _scrolls.get(scroll.getId());
	}
	
	/**
	 * Gets the support item.
	 * @param item the item
	 * @return enchant template for support item
	 */
	public EnchantSupportItem getSupportItem(ItemInstance item)
	{
		return _supports.get(item.getId());
	}
	
	/**
	 * Gets the single instance of EnchantItemData.
	 * @return single instance of EnchantItemData
	 */
	public static EnchantItemData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final EnchantItemData INSTANCE = new EnchantItemData();
	}
}
