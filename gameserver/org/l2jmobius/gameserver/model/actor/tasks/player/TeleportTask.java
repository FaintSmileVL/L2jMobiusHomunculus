package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public class TeleportTask implements Runnable
{
	private final PlayerInstance _player;
	private final Location _loc;
	
	public TeleportTask(PlayerInstance player, Location loc)
	{
		_player = player;
		_loc = loc;
	}
	
	@Override
	public void run()
	{
		if ((_player != null) && _player.isOnline())
		{
			_player.teleToLocation(_loc, true);
		}
	}
}
