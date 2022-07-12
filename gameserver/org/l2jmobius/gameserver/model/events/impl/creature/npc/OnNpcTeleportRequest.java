package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.teleporter.TeleportHolder;
import org.l2jmobius.gameserver.model.teleporter.TeleportLocation;

/**
 * Player teleport request listner - called from {@link TeleportHolder#doTeleport(PlayerInstance, Npc, int)}
 * @author malyelfik
 */
public class OnNpcTeleportRequest implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Npc _npc;
	private final TeleportLocation _loc;
	
	public OnNpcTeleportRequest(PlayerInstance player, Npc npc, TeleportLocation loc)
	{
		_player = player;
		_npc = npc;
		_loc = loc;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	public TeleportLocation getLocation()
	{
		return _loc;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_TELEPORT_REQUEST;
	}
}
