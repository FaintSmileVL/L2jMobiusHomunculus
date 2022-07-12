package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Task dedicated to make damage to the player while drowning.
 * @author UnAfraid
 */
public class WaterTask implements Runnable
{
	private final PlayerInstance _player;
	
	public WaterTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			double reduceHp = _player.getMaxHp() / 100.0;
			if (reduceHp < 1)
			{
				reduceHp = 1;
			}
			
			_player.reduceCurrentHp(reduceHp, _player, null, false, true, false, false);
			// reduced hp, becouse not rest
			final SystemMessage sm = new SystemMessage(SystemMessageId.YOU_HAVE_TAKEN_S1_DAMAGE_BECAUSE_YOU_WERE_UNABLE_TO_BREATHE);
			sm.addInt((int) reduceHp);
			_player.sendPacket(sm);
		}
	}
}
