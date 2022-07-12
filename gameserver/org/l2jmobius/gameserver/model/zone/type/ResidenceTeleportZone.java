package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneRespawn;

/**
 * based on Kerberos work for custom CastleTeleportZone
 * @author Nyaran
 */
public class ResidenceTeleportZone extends ZoneRespawn
{
	private int _residenceId;
	
	public ResidenceTeleportZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("residenceId"))
		{
			_residenceId = Integer.parseInt(value);
		}
		else
		{
			super.setParameter(name, value);
		}
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		creature.setInsideZone(ZoneId.NO_SUMMON_FRIEND, true); // FIXME: Custom ?
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		creature.setInsideZone(ZoneId.NO_SUMMON_FRIEND, false); // FIXME: Custom ?
	}
	
	@Override
	public void oustAllPlayers()
	{
		for (PlayerInstance player : getPlayersInside())
		{
			if ((player != null) && player.isOnline())
			{
				player.teleToLocation(getSpawnLoc(), 200);
			}
		}
	}
	
	public int getResidenceId()
	{
		return _residenceId;
	}
}
