package org.l2jmobius.gameserver.model;

import java.util.Calendar;

/**
 * @author UnAfraid
 */
public class SiegeScheduleDate
{
	private final int _day;
	private final int _hour;
	private final int _maxConcurrent;
	private final boolean _siegeEnabled;
	
	public SiegeScheduleDate(StatSet set)
	{
		_day = set.getInt("day", Calendar.SUNDAY);
		_hour = set.getInt("hour", 16);
		_maxConcurrent = set.getInt("maxConcurrent", 5);
		_siegeEnabled = set.getBoolean("siegeEnabled", false);
	}
	
	public int getDay()
	{
		return _day;
	}
	
	public int getHour()
	{
		return _hour;
	}
	
	public int getMaxConcurrent()
	{
		return _maxConcurrent;
	}
	
	public boolean siegeEnabled()
	{
		return _siegeEnabled;
	}
}
