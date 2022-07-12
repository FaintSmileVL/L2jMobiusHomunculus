package org.l2jmobius.gameserver.model.events.impl.sieges;

import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.siege.FortSiege;

/**
 * @author UnAfraid
 */
public class OnFortSiegeFinish implements IBaseEvent
{
	private final FortSiege _siege;
	
	public OnFortSiegeFinish(FortSiege siege)
	{
		_siege = siege;
	}
	
	public FortSiege getSiege()
	{
		return _siege;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_FORT_SIEGE_FINISH;
	}
}
