package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnNpcCanBeSeen implements IBaseEvent
{
	private final Npc _npc;
	private final PlayerInstance _player;
	
	public OnNpcCanBeSeen(Npc npc, PlayerInstance player)
	{
		_npc = npc;
		_player = player;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _player;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_CAN_BE_SEEN;
	}
}
