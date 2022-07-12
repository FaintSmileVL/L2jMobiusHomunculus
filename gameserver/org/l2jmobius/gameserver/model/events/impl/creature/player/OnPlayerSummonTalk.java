package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author St3eT
 */
public class OnPlayerSummonTalk implements IBaseEvent
{
	private final Summon _summon;
	
	public OnPlayerSummonTalk(Summon summon)
	{
		_summon = summon;
	}
	
	public Summon getSummon()
	{
		return _summon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_SUMMON_TALK;
	}
}
