package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * The Monster Derby Track Zone
 * @author durgus
 */
public class DerbyTrackZone extends ZoneType
{
	public DerbyTrackZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		if (creature.isPlayable())
		{
			creature.setInsideZone(ZoneId.MONSTER_TRACK, true);
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayable())
		{
			creature.setInsideZone(ZoneId.MONSTER_TRACK, false);
		}
	}
}
