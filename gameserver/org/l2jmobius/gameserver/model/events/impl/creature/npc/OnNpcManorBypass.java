package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author malyelfik
 */
public class OnNpcManorBypass implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Npc _target;
	private final int _request;
	private final int _manorId;
	private final boolean _nextPeriod;
	
	public OnNpcManorBypass(PlayerInstance player, Npc target, int request, int manorId, boolean nextPeriod)
	{
		_player = player;
		_target = target;
		_request = request;
		_manorId = manorId;
		_nextPeriod = nextPeriod;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _player;
	}
	
	public Npc getTarget()
	{
		return _target;
	}
	
	public int getRequest()
	{
		return _request;
	}
	
	public int getManorId()
	{
		return _manorId;
	}
	
	public boolean isNextPeriod()
	{
		return _nextPeriod;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_MANOR_BYPASS;
	}
}