package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnAttackableFactionCall implements IBaseEvent
{
	private final Npc _npc;
	private final Npc _caller;
	private final PlayerInstance _attacker;
	private final boolean _isSummon;
	
	public OnAttackableFactionCall(Npc npc, Npc caller, PlayerInstance attacker, boolean isSummon)
	{
		_npc = npc;
		_caller = caller;
		_attacker = attacker;
		_isSummon = isSummon;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	public Npc getCaller()
	{
		return _caller;
	}
	
	public PlayerInstance getAttacker()
	{
		return _attacker;
	}
	
	public boolean isSummon()
	{
		return _isSummon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ATTACKABLE_FACTION_CALL;
	}
}
