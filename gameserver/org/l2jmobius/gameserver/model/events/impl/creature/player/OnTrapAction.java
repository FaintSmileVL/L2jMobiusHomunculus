package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.enums.TrapAction;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.TrapInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnTrapAction implements IBaseEvent
{
	private final TrapInstance _trap;
	private final Creature _trigger;
	private final TrapAction _action;
	
	public OnTrapAction(TrapInstance trap, Creature trigger, TrapAction action)
	{
		_trap = trap;
		_trigger = trigger;
		_action = action;
	}
	
	public TrapInstance getTrap()
	{
		return _trap;
	}
	
	public Creature getTrigger()
	{
		return _trigger;
	}
	
	public TrapAction getAction()
	{
		return _action;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_TRAP_ACTION;
	}
}
