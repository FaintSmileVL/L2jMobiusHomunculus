package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnAttackableHate implements IBaseEvent
{
	private final Attackable _npc;
	private final PlayerInstance _player;
	private final boolean _isSummon;
	
	public OnAttackableHate(Attackable npc, PlayerInstance player, boolean isSummon)
	{
		_npc = npc;
		_player = player;
		_isSummon = isSummon;
	}
	
	public Attackable getNpc()
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
		return EventType.ON_NPC_HATE;
	}
}