package org.l2jmobius.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.ThreadPool;
import util.Chronos;
import org.l2jmobius.gameserver.model.Spawn;
import org.l2jmobius.gameserver.model.actor.Npc;

/**
 * @author Mobius
 */
public class RespawnTaskManager
{
	private static final Map<Npc, Long> PENDING_RESPAWNS = new ConcurrentHashMap<>();
	private static boolean _working = false;
	
	public RespawnTaskManager()
	{
		ThreadPool.scheduleAtFixedRate(() ->
		{
			if (_working)
			{
				return;
			}
			_working = true;
			
			final long time = Chronos.currentTimeMillis();
			for (Entry<Npc, Long> entry : PENDING_RESPAWNS.entrySet())
			{
				if (time > entry.getValue().longValue())
				{
					final Npc npc = entry.getKey();
					PENDING_RESPAWNS.remove(npc);
					final Spawn spawn = npc.getSpawn();
					if (spawn != null)
					{
						spawn.respawnNpc(npc);
						spawn._scheduledCount--;
					}
				}
			}
			
			_working = false;
		}, 0, 1000);
	}
	
	public void add(Npc npc, long time)
	{
		PENDING_RESPAWNS.put(npc, time);
	}
	
	public static RespawnTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RespawnTaskManager INSTANCE = new RespawnTaskManager();
	}
}
