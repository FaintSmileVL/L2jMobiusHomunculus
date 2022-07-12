package org.l2jmobius.gameserver.model;

import java.util.List;

import org.l2jmobius.gameserver.model.interfaces.IIdentifiable;

/**
 * @author malyelfik
 */
public class TowerSpawn implements IIdentifiable
{
	private final int _npcId;
	private final Location _location;
	private List<Integer> _zoneList = null;
	private int _upgradeLevel = 0;
	
	public TowerSpawn(int npcId, Location location)
	{
		_location = location;
		_npcId = npcId;
	}
	
	public TowerSpawn(int npcId, Location location, List<Integer> zoneList)
	{
		_location = location;
		_npcId = npcId;
		_zoneList = zoneList;
	}
	
	/**
	 * Gets the NPC ID.
	 * @return the NPC ID
	 */
	@Override
	public int getId()
	{
		return _npcId;
	}
	
	public Location getLocation()
	{
		return _location;
	}
	
	public List<Integer> getZoneList()
	{
		return _zoneList;
	}
	
	public void setUpgradeLevel(int level)
	{
		_upgradeLevel = level;
	}
	
	public int getUpgradeLevel()
	{
		return _upgradeLevel;
	}
}