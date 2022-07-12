package org.l2jmobius.gameserver.model.zone.type;

import java.util.EnumMap;
import java.util.Map;

import org.l2jmobius.gameserver.enums.Race;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneRespawn;

/**
 * Respawn zone implementation.
 * @author Nyaran
 */
public class RespawnZone extends ZoneRespawn
{
	private final Map<Race, String> _raceRespawnPoint = new EnumMap<>(Race.class);
	
	public RespawnZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
	}
	
	@Override
	protected void onExit(Creature creature)
	{
	}
	
	public void addRaceRespawnPoint(String race, String point)
	{
		_raceRespawnPoint.put(Race.valueOf(race), point);
	}
	
	public Map<Race, String> getAllRespawnPoints()
	{
		return _raceRespawnPoint;
	}
	
	public String getRespawnPoint(PlayerInstance player)
	{
		return _raceRespawnPoint.get(player.getRace());
	}
}
