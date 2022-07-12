package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * @author UnAfraid
 */
public class ConditionZone extends ZoneType
{
	private boolean NO_ITEM_DROP = false;
	private boolean NO_BOOKMARK = false;
	
	public ConditionZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equalsIgnoreCase("NoBookmark"))
		{
			NO_BOOKMARK = Boolean.parseBoolean(value);
		}
		else if (name.equalsIgnoreCase("NoItemDrop"))
		{
			NO_ITEM_DROP = Boolean.parseBoolean(value);
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
			if (NO_BOOKMARK)
			{
				creature.setInsideZone(ZoneId.NO_BOOKMARK, true);
			}
			if (NO_ITEM_DROP)
			{
				creature.setInsideZone(ZoneId.NO_ITEM_DROP, true);
			}
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayer())
		{
			if (NO_BOOKMARK)
			{
				creature.setInsideZone(ZoneId.NO_BOOKMARK, false);
			}
			if (NO_ITEM_DROP)
			{
				creature.setInsideZone(ZoneId.NO_ITEM_DROP, false);
			}
		}
	}
}
