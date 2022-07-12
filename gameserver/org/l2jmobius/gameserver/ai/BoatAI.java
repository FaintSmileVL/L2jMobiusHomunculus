package org.l2jmobius.gameserver.ai;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.BoatInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.VehicleDeparture;
import org.l2jmobius.gameserver.network.serverpackets.VehicleInfo;
import org.l2jmobius.gameserver.network.serverpackets.VehicleStarted;

/**
 * @author DS
 */
public class BoatAI extends VehicleAI
{
	public BoatAI(BoatInstance boat)
	{
		super(boat);
	}
	
	@Override
	protected void moveTo(int x, int y, int z)
	{
		if (!_actor.isMovementDisabled())
		{
			if (!_clientMoving)
			{
				_actor.broadcastPacket(new VehicleStarted(getActor(), 1));
			}
			
			_clientMoving = true;
			_actor.moveToLocation(x, y, z, 0);
			_actor.broadcastPacket(new VehicleDeparture(getActor()));
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
			_actor.broadcastPacket(new VehicleStarted(getActor(), 0));
			_actor.broadcastPacket(new VehicleInfo(getActor()));
		}
	}
	
	@Override
	public void describeStateToPlayer(PlayerInstance player)
	{
		if (_clientMoving)
		{
			player.sendPacket(new VehicleDeparture(getActor()));
		}
	}
	
	@Override
	public BoatInstance getActor()
	{
		return (BoatInstance) _actor;
	}
}