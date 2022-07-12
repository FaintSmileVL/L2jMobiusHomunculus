package org.l2jmobius.gameserver.ai;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.AirShipInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.ExMoveToLocationAirShip;
import org.l2jmobius.gameserver.network.serverpackets.ExStopMoveAirShip;

/**
 * @author DS
 */
public class AirShipAI extends VehicleAI
{
	public AirShipAI(AirShipInstance airShip)
	{
		super(airShip);
	}
	
	@Override
	protected void moveTo(int x, int y, int z)
	{
		if (!_actor.isMovementDisabled())
		{
			_clientMoving = true;
			_actor.moveToLocation(x, y, z, 0);
			_actor.broadcastPacket(new ExMoveToLocationAirShip(getActor()));
		}
	}
	
	@Override
	public void clientStopMoving(Location loc)
	{
		if (_actor.isMoving())
		{
			_actor.stopMove(loc);
		}
		
		if (_clientMoving || (loc != null))
		{
			_clientMoving = false;
			_actor.broadcastPacket(new ExStopMoveAirShip(getActor()));
		}
	}
	
	@Override
	public void describeStateToPlayer(PlayerInstance player)
	{
		if (_clientMoving)
		{
			player.sendPacket(new ExMoveToLocationAirShip(getActor()));
		}
	}
	
	@Override
	public AirShipInstance getActor()
	{
		return (AirShipInstance) _actor;
	}
}