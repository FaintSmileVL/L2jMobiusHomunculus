package org.l2jmobius.gameserver.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.l2jmobius.Config;
import util.Rnd;
import org.l2jmobius.gameserver.enums.Race;

/**
 * @author Nyaran
 */
public class MapRegion
{
	private final String _name;
	private final String _town;
	private final int _locId;
	private final int _bbs;
	private List<int[]> _maps = null;
	
	private List<Location> _spawnLocs = null;
	private List<Location> _otherSpawnLocs = null;
	private List<Location> _chaoticSpawnLocs = null;
	private List<Location> _banishSpawnLocs = null;
	
	private final Map<Race, String> _bannedRace = new EnumMap<>(Race.class);
	
	public MapRegion(String name, String town, int locId, int bbs)
	{
		_name = name;
		_town = town;
		_locId = locId;
		_bbs = bbs;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getTown()
	{
		return _town;
	}
	
	public int getLocId()
	{
		return _locId;
	}
	
	public int getBbs()
	{
		return _bbs;
	}
	
	public void addMap(int x, int y)
	{
		if (_maps == null)
		{
			_maps = new ArrayList<>();
		}
		
		_maps.add(new int[]
		{
			x,
			y
		});
	}
	
	public List<int[]> getMaps()
	{
		return _maps;
	}
	
	public boolean isZoneInRegion(int x, int y)
	{
		if (_maps == null)
		{
			return false;
		}
		
		for (int[] map : _maps)
		{
			if ((map[0] == x) && (map[1] == y))
			{
				return true;
			}
		}
		return false;
	}
	
	// Respawn
	public void addSpawn(int x, int y, int z)
	{
		if (_spawnLocs == null)
		{
			_spawnLocs = new ArrayList<>();
		}
		
		_spawnLocs.add(new Location(x, y, z));
	}
	
	public void addOtherSpawn(int x, int y, int z)
	{
		if (_otherSpawnLocs == null)
		{
			_otherSpawnLocs = new ArrayList<>();
		}
		
		_otherSpawnLocs.add(new Location(x, y, z));
	}
	
	public void addChaoticSpawn(int x, int y, int z)
	{
		if (_chaoticSpawnLocs == null)
		{
			_chaoticSpawnLocs = new ArrayList<>();
		}
		
		_chaoticSpawnLocs.add(new Location(x, y, z));
	}
	
	public void addBanishSpawn(int x, int y, int z)
	{
		if (_banishSpawnLocs == null)
		{
			_banishSpawnLocs = new ArrayList<>();
		}
		
		_banishSpawnLocs.add(new Location(x, y, z));
	}
	
	public List<Location> getSpawns()
	{
		return _spawnLocs;
	}
	
	public Location getSpawnLoc()
	{
		if (Config.RANDOM_RESPAWN_IN_TOWN_ENABLED)
		{
			return _spawnLocs.get(Rnd.get(_spawnLocs.size()));
		}
		return _spawnLocs.get(0);
	}
	
	public Location getOtherSpawnLoc()
	{
		if (_otherSpawnLocs != null)
		{
			if (Config.RANDOM_RESPAWN_IN_TOWN_ENABLED)
			{
				return _otherSpawnLocs.get(Rnd.get(_otherSpawnLocs.size()));
			}
			return _otherSpawnLocs.get(0);
		}
		return getSpawnLoc();
	}
	
	public Location getChaoticSpawnLoc()
	{
		if (_chaoticSpawnLocs != null)
		{
			if (Config.RANDOM_RESPAWN_IN_TOWN_ENABLED)
			{
				return _chaoticSpawnLocs.get(Rnd.get(_chaoticSpawnLocs.size()));
			}
			return _chaoticSpawnLocs.get(0);
		}
		return getSpawnLoc();
	}
	
	public Location getBanishSpawnLoc()
	{
		if (_banishSpawnLocs != null)
		{
			if (Config.RANDOM_RESPAWN_IN_TOWN_ENABLED)
			{
				return _banishSpawnLocs.get(Rnd.get(_banishSpawnLocs.size()));
			}
			return _banishSpawnLocs.get(0);
		}
		return getSpawnLoc();
	}
	
	public void addBannedRace(String race, String point)
	{
		_bannedRace.put(Race.valueOf(race), point);
	}
	
	public Map<Race, String> getBannedRace()
	{
		return _bannedRace;
	}
}
