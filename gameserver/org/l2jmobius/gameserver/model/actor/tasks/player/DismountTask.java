package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated to dismount player from pet.
 * @author UnAfraid
 */
public class DismountTask implements Runnable
{
	private final PlayerInstance _player;
	
	public DismountTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.dismount();
		}
	}
}
