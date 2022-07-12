package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.network.serverpackets.UserInfo;

/**
 * Task dedicated to reward player with fame while standing on siege zone.
 * @author UnAfraid
 */
public class FameTask implements Runnable
{
	private final PlayerInstance _player;
	private final int _value;
	
	public FameTask(PlayerInstance player, int value)
	{
		_player = player;
		_value = value;
	}
	
	@Override
	public void run()
	{
		if ((_player == null) || (_player.isDead() && !Config.FAME_FOR_DEAD_PLAYERS))
		{
			return;
		}
		if (((_player.getClient() == null) || _player.getClient().isDetached()) && !Config.OFFLINE_FAME)
		{
			return;
		}
		_player.setFame(_player.getFame() + _value);
		final SystemMessage sm = new SystemMessage(SystemMessageId.YOU_HAVE_ACQUIRED_S1_FAME);
		sm.addInt(_value);
		_player.sendPacket(sm);
		_player.sendPacket(new UserInfo(_player));
	}
}
