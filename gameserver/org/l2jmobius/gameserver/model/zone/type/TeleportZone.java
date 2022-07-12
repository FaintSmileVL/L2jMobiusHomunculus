package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * @author Sdw
 */
public class TeleportZone extends ZoneType
{
	private int _x = -1;
	private int _y = -1;
	private int _z = -1;
	
	public TeleportZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		switch (name)
		{
			case "oustX":
			{
				_x = Integer.parseInt(value);
				break;
			}
			case "oustY":
			{
				_y = Integer.parseInt(value);
				break;
			}
			case "oustZ":
			{
				_z = Integer.parseInt(value);
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
		creature.teleToLocation(new Location(_x, _y, _z));
	}
	
	@Override
	protected void onExit(Creature creature)
	{
	}
}