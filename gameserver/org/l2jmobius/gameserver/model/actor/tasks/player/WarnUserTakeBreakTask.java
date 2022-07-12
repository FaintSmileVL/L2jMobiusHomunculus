package org.l2jmobius.gameserver.model.actor.tasks.player;

import java.util.concurrent.TimeUnit;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Task dedicated to warn user to take a break.
 * @author UnAfraid
 */
public class WarnUserTakeBreakTask implements Runnable
{
	private final PlayerInstance _player;
	
	public WarnUserTakeBreakTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			if (_player.isOnline())
			{
				final long hours = TimeUnit.MILLISECONDS.toHours(_player.getUptime());
				_player.sendPacket(new SystemMessage(SystemMessageId.YOU_HAVE_PLAYED_FOR_S1_H_TAKE_A_BREAK_PLEASE).addLong(hours));
			}
			else
			{
				_player.stopWarnUserTakeBreak();
			}
		}
	}
}
