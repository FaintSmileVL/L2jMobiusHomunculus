package org.l2jmobius.gameserver.model;

import java.util.LinkedList;
import java.util.List;

import org.l2jmobius.gameserver.model.interfaces.ILocational;

/**
 * @author UnAfraid
 */
public class SayuneEntry implements ILocational
{
	private boolean _isSelector = false;
	private final int _id;
	private int _x;
	private int _y;
	private int _z;
	private final List<SayuneEntry> _innerEntries = new LinkedList<>();
	
	public SayuneEntry(int id)
	{
		_id = id;
	}
	
	public SayuneEntry(boolean isSelector, int id, int x, int y, int z)
	{
		_isSelector = isSelector;
		_id = id;
		_x = x;
		_y = y;
		_z = z;
	}
	
	public int getId()
	{
		return _id;
	}
	
	@Override
	public int getX()
	{
		return _x;
	}
	
	@Override
	public int getY()
	{
		return _y;
	}
	
	@Override
	public int getZ()
	{
		return _z;
	}
	
	@Override
	public int getHeading()
	{
		return 0;
	}
	
	@Override
	public ILocational getLocation()
	{
		return new Location(_x, _y, _z);
	}
	
	public boolean isSelector()
	{
		return _isSelector;
	}
	
	public List<SayuneEntry> getInnerEntries()
	{
		return _innerEntries;
	}
	
	public SayuneEntry addInnerEntry(SayuneEntry innerEntry)
	{
		_innerEntries.add(innerEntry);
		return innerEntry;
	}
}
