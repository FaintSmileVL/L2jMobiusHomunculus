package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated watch for player teleportation.
 * @author UnAfraid
 */
public class TeleportWatchdogTask implements Runnable
{
	private final PlayerInstance _player;
	
	public TeleportWatchdogTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if ((_player == null) || !_player.isTeleporting())
		{
			return;
		}
		_player.onTeleported();
	}
}
