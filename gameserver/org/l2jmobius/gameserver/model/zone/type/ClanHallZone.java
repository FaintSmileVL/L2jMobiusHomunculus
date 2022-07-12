package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.data.xml.ClanHallData;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.residences.ClanHall;
import org.l2jmobius.gameserver.model.zone.ZoneId;

/**
 * A clan hall zone
 * @author durgus
 */
public class ClanHallZone extends ResidenceZone
{
	public ClanHallZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("clanHallId"))
		{
			setResidenceId(Integer.parseInt(value));
		}
		else
		{
			super.setParameter(name, value);
		}
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.CLAN_HALL, true);
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.CLAN_HALL, false);
		}
	}
	
	@Override
	public Location getBanishSpawnLoc()
	{
		final ClanHall clanHall = ClanHallData.getInstance().getClanHallById(getResidenceId());
		if (clanHall == null)
		{
			return null;
		}
		return clanHall.getBanishLocation();
	}
}