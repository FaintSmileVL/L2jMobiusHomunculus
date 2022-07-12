package org.l2jmobius.gameserver.model.actor.stat;

import org.l2jmobius.gameserver.model.actor.instance.ControllableAirShipInstance;

public class ControllableAirShipStat extends VehicleStat
{
	public ControllableAirShipStat(ControllableAirShipInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public ControllableAirShipInstance getActiveChar()
	{
		return (ControllableAirShipInstance) super.getActiveChar();
	}
	
	@Override
	public double getMoveSpeed()
	{
		if (getActiveChar().isInDock() || (getActiveChar().getFuel() > 0))
		{
			return super.getMoveSpeed();
		}
		return super.getMoveSpeed() * 0.05f;
	}
}