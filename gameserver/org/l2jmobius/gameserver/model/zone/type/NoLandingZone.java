package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.enums.MountType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * A no landing zone
 * @author durgus
 */
public class NoLandingZone extends ZoneType
{
	private int dismountDelay = 5;
	
	public NoLandingZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("dismountDelay"))
		{
			dismountDelay = Integer.parseInt(value);
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
			creature.setInsideZone(ZoneId.NO_LANDING, true);
			if (creature.getActingPlayer().getMountType() == MountType.WYVERN)
			{
				creature.sendPacket(SystemMessageId.THIS_AREA_CANNOT_BE_ENTERED_WHILE_MOUNTED_ATOP_OF_A_WYVERN_YOU_WILL_BE_DISMOUNTED_FROM_YOUR_WYVERN_IF_YOU_DO_NOT_LEAVE);
				creature.getActingPlayer().enteredNoLanding(dismountDelay);
			}
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.NO_LANDING, false);
			if (creature.getActingPlayer().getMountType() == MountType.WYVERN)
			{
				creature.getActingPlayer().exitedNoLanding();
			}
		}
	}
}
