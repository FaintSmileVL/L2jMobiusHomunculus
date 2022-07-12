package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated to enable player's inventory.
 * @author UnAfraid
 */
public class InventoryEnableTask implements Runnable
{
	private final PlayerInstance _player;
	
	public InventoryEnableTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.setInventoryBlockingStatus(false);
		}
	}
}
