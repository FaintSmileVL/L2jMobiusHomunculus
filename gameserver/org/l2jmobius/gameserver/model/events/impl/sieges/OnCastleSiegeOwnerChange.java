package org.l2jmobius.gameserver.model.events.impl.sieges;

import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.siege.Siege;

/**
 * @author UnAfraid
 */
public class OnCastleSiegeOwnerChange implements IBaseEvent
{
	private final Siege _siege;
	
	public OnCastleSiegeOwnerChange(Siege siege)
	{
		_siege = siege;
	}
	
	public Siege getSiege()
	{
		return _siege;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CASTLE_SIEGE_OWNER_CHANGE;
	}
}
