package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.holders.ClanShopProductHolder;
import org.l2jmobius.gameserver.model.items.Item;

/**
 * @author Mobius
 */
public class ClanShopData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(ClanShopData.class.getName());
	
	private final List<ClanShopProductHolder> _clanShopProducts = new ArrayList<>();
	
	protected ClanShopData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_clanShopProducts.clear();
		
		parseDatapackFile("config/ClanShop.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _clanShopProducts.size() + " clan shop products.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "clan", productNode ->
		{
			final StatSet set = new StatSet(parseAttributes(productNode));
			final int clanLevel = set.getInt("level");
			final int itemId = set.getInt("item");
			final int count = set.getInt("count");
			final long adena = set.getLong("adena");
			final int fame = set.getInt("fame");
			final Item item = ItemTable.getInstance().getTemplate(itemId);
			if (item == null)
			{
				LOGGER.info(getClass().getSimpleName() + ": Could not create clan shop item " + itemId + ", it does not exist.");
			}
			else
			{
				_clanShopProducts.add(new ClanShopProductHolder(clanLevel, item, count, adena, fame));
			}
		}));
	}
	
	public ClanShopProductHolder getProduct(int itemId)
	{
		for (ClanShopProductHolder product : _clanShopProducts)
		{
			if (product.getTradeItem().getItem().getId() == itemId)
			{
				return product;
			}
		}
		return null;
	}
	
	public List<ClanShopProductHolder> getProducts()
	{
		return _clanShopProducts;
	}
	
	public static ClanShopData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ClanShopData INSTANCE = new ClanShopData();
	}
}
