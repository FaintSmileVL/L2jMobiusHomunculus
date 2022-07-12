package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * A scripted zone... Creation of such a zone should require somekind of script reference which can handle onEnter() / onExit()
 * @author durgus
 */
public class ScriptZone extends ZoneType
{
	public ScriptZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		creature.setInsideZone(ZoneId.SCRIPT, true);
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		creature.setInsideZone(ZoneId.SCRIPT, false);
	}
}
