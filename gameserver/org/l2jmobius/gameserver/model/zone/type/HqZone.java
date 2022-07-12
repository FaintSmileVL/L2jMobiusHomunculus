package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * Zone where 'Build Headquarters' is allowed.
 * @author Gnacik
 */
public class HqZone extends ZoneType
{
	public HqZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if ("castleId".equals(name))
		{
			// TODO
		}
		else if ("fortId".equals(name))
		{
			// TODO
		}
		else if ("clanHallId".equals(name))
		{
			// TODO
		}
		else if ("territoryId".equals(name))
		{
			// TODO
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
			creature.setInsideZone(ZoneId.HQ, true);
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.HQ, false);
		}
	}
}
