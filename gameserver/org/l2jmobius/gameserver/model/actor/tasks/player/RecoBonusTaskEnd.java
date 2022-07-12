package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.ExVoteSystemInfo;

/**
 * Task dedicated to end player's recommendation bonus.
 * @author UnAfraid
 */
public class RecoBonusTaskEnd implements Runnable
{
	private final PlayerInstance _player;
	
	public RecoBonusTaskEnd(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.sendPacket(new ExVoteSystemInfo(_player));
		}
	}
}
