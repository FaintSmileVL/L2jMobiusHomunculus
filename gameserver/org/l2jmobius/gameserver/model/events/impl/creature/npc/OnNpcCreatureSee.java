package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * An instantly executed event when Creature is killed by Creature.
 * @author UnAfraid
 */
public class OnNpcCreatureSee implements IBaseEvent
{
	private final Npc _npc;
	private final Creature _creature;
	private final boolean _isSummon;
	
	public OnNpcCreatureSee(Npc npc, Creature creature, boolean isSummon)
	{
		_npc = npc;
		_creature = creature;
		_isSummon = isSummon;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	public Creature getCreature()
	{
		return _creature;
	}
	
	public boolean isSummon()
	{
		return _isSummon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_CREATURE_SEE;
	}
}