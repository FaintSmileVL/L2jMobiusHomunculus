package org.l2jmobius.gameserver.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import org.l2jmobius.gameserver.model.buylist.Product;

/**
 * @author Mobius
 */
public class BuyListTaskManager
{
	private static final Map<Product, Long> PRODUCTS = new ConcurrentHashMap<>();
	private static final List<Product> PENDING_UPDATES = new ArrayList<>();
	private static boolean _workingProducts = false;
	private static boolean _workingSaves = false;
	
	public BuyListTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_workingProducts)
			{
				return;
			}
			_workingProducts = true;
			
			final long currentTime = Chronos.currentTimeMillis();
			for (Entry<Product, Long> entry : PRODUCTS.entrySet())
			{
				if (currentTime > entry.getValue().longValue())
				{
					final Product product = entry.getKey();
					PRODUCTS.remove(product);
					synchronized (PENDING_UPDATES)
					{
						if (!PENDING_UPDATES.contains(product))
						{
							PENDING_UPDATES.add(product);
						}
					}
				}
			}
			
			_workingProducts = false;
		}, 1000, 60000);
		
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_workingSaves)
			{
				return;
			}
			_workingSaves = true;
			
			if (!PENDING_UPDATES.isEmpty())
			{
				final Product product;
				synchronized (PENDING_UPDATES)
				{
					product = PENDING_UPDATES.get(0);
					PENDING_UPDATES.remove(product);
				}
				product.restock();
			}
			
			_workingSaves = false;
		}, 50, 50);
	}
	
	public void add(Product product, long endTime)
	{
		if (!PRODUCTS.containsKey(product))
		{
			PRODUCTS.put(product, endTime);
		}
	}
	
	public void update(Product product, long endTime)
	{
		PRODUCTS.put(product, endTime);
	}
	
	public long getRestockDelay(Product product)
	{
		return PRODUCTS.getOrDefault(product, 0L);
	}
	
	public static BuyListTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final BuyListTaskManager INSTANCE = new BuyListTaskManager();
	}
}
