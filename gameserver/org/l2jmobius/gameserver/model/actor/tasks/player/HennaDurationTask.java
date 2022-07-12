package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Mobius
 */
public class HennaDurationTask implements Runnable
{
	private final PlayerInstance _player;
	private final int _slot;
	
	public HennaDurationTask(PlayerInstance player, int slot)
	{
		_player = player;
		_slot = slot;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.removeHenna(_slot);
		}
	}
}
