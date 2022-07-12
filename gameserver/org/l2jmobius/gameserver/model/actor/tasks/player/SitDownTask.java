package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated to put player to sit down.
 * @author UnAfraid
 */
public class SitDownTask implements Runnable
{
	private final PlayerInstance _player;
	
	public SitDownTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.setBlockActions(false);
		}
	}
}
