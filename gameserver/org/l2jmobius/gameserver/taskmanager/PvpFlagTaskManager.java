package org.l2jmobius.gameserver.taskmanager;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Mobius
 */
public class PvpFlagTaskManager
{
	private static final Set<PlayerInstance> PLAYERS = ConcurrentHashMap.newKeySet();
	private static boolean _working = false;
	
	public PvpFlagTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			if (!PLAYERS.isEmpty())
			{
				final long time = Chronos.currentTimeMillis();
				for (PlayerInstance player : PLAYERS)
				{
					if (time > player.getPvpFlagLasts())
					{
						player.stopPvPFlag();
					}
					else if (time > (player.getPvpFlagLasts() - 20000))
					{
						player.updatePvPFlag(2);
					}
					else
					{
						player.updatePvPFlag(1);
					}
				}
			}
			
			_working = false;
		}, 1000, 1000);
	}
	
	public void add(PlayerInstance player)
	{
		PLAYERS.add(player);
	}
	
	public void remove(PlayerInstance player)
	{
		PLAYERS.remove(player);
	}
	
	public static PvpFlagTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final PvpFlagTaskManager INSTANCE = new PvpFlagTaskManager();
	}
}
