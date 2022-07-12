package org.l2jmobius.gameserver.model.shuttle;

import java.util.ArrayList;
import java.util.List;

import util.Chronos;
import org.l2jmobius.gameserver.model.Location;

/**
 * @author UnAfraid
 */
public class ShuttleStop
{
	private final int _id;
	private boolean _isOpen = true;
	private final List<Location> _dimensions = new ArrayList<>(3);
	private long _lastDoorStatusChanges = Chronos.currentTimeMillis();
	
	public ShuttleStop(int id)
	{
		_id = id;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public boolean isDoorOpen()
	{
		return _isOpen;
	}
	
	public void addDimension(Location loc)
	{
		_dimensions.add(loc);
	}
	
	public List<Location> getDimensions()
	{
		return _dimensions;
	}
	
	public void openDoor()
	{
		if (_isOpen)
		{
			return;
		}
		
		_isOpen = true;
		_lastDoorStatusChanges = Chronos.currentTimeMillis();
	}
	
	public void closeDoor()
	{
		if (!_isOpen)
		{
			return;
		}
		
		_isOpen = false;
		_lastDoorStatusChanges = Chronos.currentTimeMillis();
	}
	
	public boolean hasDoorChanged()
	{
		return (Chronos.currentTimeMillis() - _lastDoorStatusChanges) <= 1000;
	}
}
