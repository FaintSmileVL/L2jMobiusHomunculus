package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * An instantly executed event when Attackable is killed by PlayerInstance.
 * @author UnAfraid
 */
public class OnAttackableKill implements IBaseEvent
{
	private final PlayerInstance _attacker;
	private final Attackable _target;
	private final boolean _isSummon;
	
	public OnAttackableKill(PlayerInstance attacker, Attackable target, boolean isSummon)
	{
		_attacker = attacker;
		_target = target;
		_isSummon = isSummon;
	}
	
	public PlayerInstance getAttacker()
	{
		return _attacker;
	}
	
	public Attackable getTarget()
	{
		return _target;
	}
	
	public boolean isSummon()
	{
		return _isSummon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ATTACKABLE_KILL;
	}
}