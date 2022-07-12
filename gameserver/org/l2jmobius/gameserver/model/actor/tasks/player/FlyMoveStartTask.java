package org.l2jmobius.gameserver.model.actor.tasks.player;

import java.util.Objects;

import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.SayuneRequest;
import org.l2jmobius.gameserver.model.zone.ZoneType;
import org.l2jmobius.gameserver.network.serverpackets.sayune.ExNotifyFlyMoveStart;

/**
 * @author UnAfraid
 */
public class FlyMoveStartTask implements Runnable
{
	private final PlayerInstance _player;
	private final ZoneType _zone;
	
	public FlyMoveStartTask(ZoneType zone, PlayerInstance player)
	{
		Objects.requireNonNull(zone);
		Objects.requireNonNull(player);
		_player = player;
		_zone = zone;
	}
	
	@Override
	public void run()
	{
		if (!_zone.isCharacterInZone(_player))
		{
			return;
		}
		
		if (!_player.hasRequest(SayuneRequest.class))
		{
			_player.sendPacket(ExNotifyFlyMoveStart.STATIC_PACKET);
			ThreadPool.schedule(this, 1000);
		}
	}
}