package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.BoatManager;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.BoatInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.GetOnVehicle;

/**
 * @version $Revision: 1.1.4.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestGetOnVehicle implements IClientIncomingPacket
{
	private int _boatId;
	private Location _pos;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_boatId = packet.readD();
		_pos = new Location(packet.readD(), packet.readD(), packet.readD());
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		BoatInstance boat;
		if (player.isInBoat())
		{
			boat = player.getBoat();
			if (boat.getObjectId() != _boatId)
			{
				client.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
		}
		else
		{
			boat = BoatManager.getInstance().getBoat(_boatId);
			if ((boat == null) || boat.isMoving() || !player.isInsideRadius3D(boat, 1000))
			{
				client.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
		}
		
		player.setInVehiclePosition(_pos);
		player.setVehicle(boat);
		player.broadcastPacket(new GetOnVehicle(player.getObjectId(), boat.getObjectId(), _pos));
		player.setXYZ(boat.getX(), boat.getY(), boat.getZ());
		player.setInsideZone(ZoneId.PEACE, true);
		player.revalidateZone(true);
	}
}
