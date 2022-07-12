package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnAttackableAggroRangeEnter implements IBaseEvent
{
	private final Npc _npc;
	private final PlayerInstance _player;
	private final boolean _isSummon;
	
	public OnAttackableAggroRangeEnter(Npc npc, PlayerInstance attacker, boolean isSummon)
	{
		_npc = npc;
		_player = attacker;
		_isSummon = isSummon;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _player;
	}
	
	public boolean isSummon()
	{
		return _isSummon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_ATTACKABLE_AGGRO_RANGE_ENTER;
	}
}
