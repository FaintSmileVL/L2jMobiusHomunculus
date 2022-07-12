package org.l2jmobius.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Mobius
 */
public class PlayerAutoSaveTaskManager
{
	private static final Map<PlayerInstance, Long> PLAYER_TIMES = new ConcurrentHashMap<>();
	private static boolean _working = false;
	
	public PlayerAutoSaveTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			final long time = Chronos.currentTimeMillis();
			SEARCH: for (Entry<PlayerInstance, Long> entry : PLAYER_TIMES.entrySet())
			{
				if (time > entry.getValue().longValue())
				{
					final PlayerInstance player = entry.getKey();
					if ((player != null) && player.isOnline())
					{
						player.autoSave();
						PLAYER_TIMES.put(entry.getKey(), time + Config.CHAR_DATA_STORE_INTERVAL);
						break SEARCH; // Prevent SQL flood.
					}
				}
			}
			
			_working = false;
		}, 1000, 1000);
	}
	
	public void add(PlayerInstance player)
	{
		PLAYER_TIMES.put(player, Chronos.currentTimeMillis() + Config.CHAR_DATA_STORE_INTERVAL);
	}
	
	public void remove(PlayerInstance player)
	{
		PLAYER_TIMES.remove(player);
	}
	
	public static PlayerAutoSaveTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final PlayerAutoSaveTaskManager INSTANCE = new PlayerAutoSaveTaskManager();
	}
}
