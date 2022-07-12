package org.l2jmobius.gameserver.model.events.impl;

import org.l2jmobius.gameserver.model.events.EventType;

/**
 * @author UnAfraid
 */
public class OnDayNightChange implements IBaseEvent
{
	private final boolean _isNight;
	
	public OnDayNightChange(boolean isNight)
	{
		_isNight = isNight;
	}
	
	public boolean isNight()
	{
		return _isNight;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_DAY_NIGHT_CHANGE;
	}
}
