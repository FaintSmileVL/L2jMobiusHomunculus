package org.l2jmobius.gameserver.instancemanager;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.model.itemauction.ItemAuctionInstance;

/**
 * @author Forsaiken
 */
public class ItemAuctionManager implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(ItemAuctionManager.class.getName());
	
	private final Map<Integer, ItemAuctionInstance> _managerInstances = new HashMap<>();
	private final AtomicInteger _auctionIds = new AtomicInteger(1);
	
	protected ItemAuctionManager()
	{
		if (!Config.ALT_ITEM_AUCTION_ENABLED)
		{
			LOGGER.info("Disabled by config.");
			return;
		}
		
		try (Connection con = DatabaseFactory.getConnection();
			Statement statement = con.createStatement();
			ResultSet rset = statement.executeQuery("SELECT auctionId FROM item_auction ORDER BY auctionId DESC LIMIT 0, 1"))
		{
			if (rset.next())
			{
				_auctionIds.set(rset.getInt(1) + 1);
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.SEVERE, "Failed loading auctions.", e);
		}
		
		load();
	}
	
	@Override
	public void load()
	{
		_managerInstances.clear();
		parseDatapackFile("data/ItemAuctions.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _managerInstances.size() + " instances.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		try
		{
			for (Node na = doc.getFirstChild(); na != null; na = na.getNextSibling())
			{
				if ("list".equalsIgnoreCase(na.getNodeName()))
				{
					for (Node nb = na.getFirstChild(); nb != null; nb = nb.getNextSibling())
					{
						if ("instance".equalsIgnoreCase(nb.getNodeName()))
						{
							final NamedNodeMap nab = nb.getAttributes();
							final int instanceId = Integer.parseInt(nab.getNamedItem("id").getNodeValue());
							if (_managerInstances.containsKey(instanceId))
							{
								throw new Exception("Dublicated instanceId " + instanceId);
							}
							
							final ItemAuctionInstance instance = new ItemAuctionInstance(instanceId, _auctionIds, nb);
							_managerInstances.put(instanceId, instance);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, getClass().getSimpleName() + ": Failed loading auctions from xml.", e);
		}
	}
	
	public void shutdown()
	{
		for (ItemAuctionInstance instance : _managerInstances.values())
		{
			instance.shutdown();
		}
	}
	
	public ItemAuctionInstance getManagerInstance(int instanceId)
	{
		return _managerInstances.get(instanceId);
	}
	
	public int getNextAuctionId()
	{
		return _auctionIds.getAndIncrement();
	}
	
	public static void deleteAuction(int auctionId)
	{
		try (Connection con = DatabaseFactory.getConnection())
		{
			try (PreparedStatement statement = con.prepareStatement("DELETE FROM item_auction WHERE auctionId=?"))
			{
				statement.setInt(1, auctionId);
				statement.execute();
			}
			
			try (PreparedStatement statement = con.prepareStatement("DELETE FROM item_auction_bid WHERE auctionId=?"))
			{
				statement.setInt(1, auctionId);
				statement.execute();
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.SEVERE, "ItemAuctionManagerInstance: Failed deleting auction: " + auctionId, e);
		}
	}
	
	/**
	 * Gets the single instance of {@code ItemAuctionManager}.
	 * @return single instance of {@code ItemAuctionManager}
	 */
	public static ItemAuctionManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ItemAuctionManager INSTANCE = new ItemAuctionManager();
	}
}