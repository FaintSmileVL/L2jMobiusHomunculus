package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated to reset player's current souls.
 * @author UnAfraid
 */
public class ResetSoulsTask implements Runnable
{
	private final PlayerInstance _player;
	
	public ResetSoulsTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.clearSouls();
		}
	}
}
