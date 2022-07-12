package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.Location;

/**
 * @author St3eT
 */
public class SpawnHolder extends Location
{
	private final int _npcId;
	private final int _respawnDelay;
	private final boolean _spawnAnimation;
	
	public SpawnHolder(int npcId, int x, int y, int z, int heading, boolean spawnAnimation)
	{
		super(x, y, z, heading);
		_npcId = npcId;
		_respawnDelay = 0;
		_spawnAnimation = spawnAnimation;
	}
	
	public SpawnHolder(int npcId, int x, int y, int z, int heading, int respawn, boolean spawnAnimation)
	{
		super(x, y, z, heading);
		_npcId = npcId;
		_respawnDelay = respawn;
		_spawnAnimation = spawnAnimation;
	}
	
	public SpawnHolder(int npcId, Location loc, boolean spawnAnimation)
	{
		super(loc.getX(), loc.getY(), loc.getZ(), loc.getHeading());
		_npcId = npcId;
		_respawnDelay = 0;
		_spawnAnimation = spawnAnimation;
	}
	
	public SpawnHolder(int npcId, Location loc, int respawn, boolean spawnAnimation)
	{
		super(loc.getX(), loc.getY(), loc.getZ(), loc.getHeading());
		_npcId = npcId;
		_respawnDelay = respawn;
		_spawnAnimation = spawnAnimation;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public boolean isSpawnAnimation()
	{
		return _spawnAnimation;
	}
	
	public int getRespawnDelay()
	{
		return _respawnDelay;
	}
}