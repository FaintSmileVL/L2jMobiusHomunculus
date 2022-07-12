package org.l2jmobius.gameserver.model.zone.type;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author BiggBoss
 */
public class SiegableHallZone extends ClanHallZone
{
	private List<Location> _challengerLocations;
	
	public SiegableHallZone(int id)
	{
		super(id);
	}
	
	@Override
	public void parseLoc(int x, int y, int z, String type)
	{
		if ((type != null) && type.equals("challenger"))
		{
			if (_challengerLocations == null)
			{
				_challengerLocations = new ArrayList<>();
			}
			_challengerLocations.add(new Location(x, y, z));
		}
		else
		{
			super.parseLoc(x, y, z, type);
		}
	}
	
	public List<Location> getChallengerSpawns()
	{
		return _challengerLocations;
	}
	
	public void banishNonSiegeParticipants()
	{
		for (PlayerInstance player : getPlayersInside())
		{
			if ((player != null) && player.isInHideoutSiege())
			{
				player.teleToLocation(getBanishSpawnLoc(), true);
			}
		}
	}
}
