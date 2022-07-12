package org.l2jmobius.gameserver.cache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author -Nemesiss-
 */
public class WarehouseCacheManager
{
	protected static final Map<PlayerInstance, Long> CACHED_WH = new ConcurrentHashMap<>();
	protected static final long CACHE_TIME = Config.WAREHOUSE_CACHE_TIME * 60000;
	
	protected WarehouseCacheManager()
	{
		ThreadPool.scheduleAtFixedRate(new CacheScheduler(), 120000, 60000);
	}
	
	public void addCacheTask(PlayerInstance pc)
	{
		CACHED_WH.put(pc, Chronos.currentTimeMillis());
	}
	
	public void remCacheTask(PlayerInstance pc)
	{
		CACHED_WH.remove(pc);
	}
	
	private class CacheScheduler implements Runnable
	{
		public CacheScheduler()
		{
		}
		
		@Override
		public void run()
		{
			final long cTime = Chronos.currentTimeMillis();
			for (Entry<PlayerInstance, Long> entry : CACHED_WH.entrySet())
			{
				if ((cTime - entry.getValue().longValue()) > CACHE_TIME)
				{
					final PlayerInstance player = entry.getKey();
					player.clearWarehouse();
					CACHED_WH.remove(player);
				}
			}
		}
	}
	
	public static WarehouseCacheManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final WarehouseCacheManager INSTANCE = new WarehouseCacheManager();
	}
}
