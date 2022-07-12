package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated to reset player's current charges.
 * @author UnAfraid
 */
public class ResetChargesTask implements Runnable
{
	private final PlayerInstance _player;
	
	public ResetChargesTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.clearCharges();
		}
	}
}
