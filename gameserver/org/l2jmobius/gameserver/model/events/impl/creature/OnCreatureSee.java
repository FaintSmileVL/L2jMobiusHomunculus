package org.l2jmobius.gameserver.model.events.impl.creature;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnCreatureSee implements IBaseEvent
{
	private final Creature _seer;
	private final Creature _seen;
	
	public OnCreatureSee(Creature seer, Creature seen)
	{
		_seer = seer;
		_seen = seen;
	}
	
	public Creature getSeer()
	{
		return _seer;
	}
	
	public Creature getSeen()
	{
		return _seen;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CREATURE_SEE;
	}
}