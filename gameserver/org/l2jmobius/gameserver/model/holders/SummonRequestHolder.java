package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Mobius
 */
public class SummonRequestHolder
{
	private final PlayerInstance _summoner;
	private final Location _location;
	
	public SummonRequestHolder(PlayerInstance summoner)
	{
		_summoner = summoner;
		_location = new Location(summoner.getX(), summoner.getY(), summoner.getZ(), summoner.getHeading());
	}
	
	public PlayerInstance getSummoner()
	{
		return _summoner;
	}
	
	public Location getLocation()
	{
		return _location;
	}
}
