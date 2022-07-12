package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnNpcMoveFinished implements IBaseEvent
{
	private final Npc _npc;
	
	public OnNpcMoveFinished(Npc npc)
	{
		_npc = npc;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_MOVE_FINISHED;
	}
}
