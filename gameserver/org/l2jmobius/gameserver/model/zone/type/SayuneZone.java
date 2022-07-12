package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.tasks.player.FlyMoveStartTask;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * @author UnAfraid
 */
public class SayuneZone extends ZoneType
{
	private int _mapId = -1;
	
	public SayuneZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		switch (name)
		{
			case "mapId":
			{
				_mapId = Integer.parseInt(value);
				break;
			}
			default:
			{
				super.setParameter(name, value);
			}
		}
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		if (creature.isPlayer() && (creature.isInCategory(CategoryType.SIXTH_CLASS_GROUP) || Config.FREE_JUMPS_FOR_ALL) && !creature.getActingPlayer().isMounted() && !creature.isTransformed())
		{
			creature.setInsideZone(ZoneId.SAYUNE, true);
			ThreadPool.execute(new FlyMoveStartTask(this, creature.getActingPlayer()));
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.SAYUNE, false);
		}
	}
	
	public int getMapId()
	{
		return _mapId;
	}
}
