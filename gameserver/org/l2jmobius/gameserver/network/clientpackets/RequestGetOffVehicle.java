package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.GetOffVehicle;
import org.l2jmobius.gameserver.network.serverpackets.StopMoveInVehicle;

/**
 * @author Maktakien
 */
public class RequestGetOffVehicle implements IClientIncomingPacket
{
	private int _boatId;
	private int _x;
	private int _y;
	private int _z;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_boatId = packet.readD();
		_x = packet.readD();
		_y = packet.readD();
		_z = packet.readD();
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
		if (!player.isInBoat() || (player.getBoat().getObjectId() != _boatId) || player.getBoat().isMoving() || !player.isInsideRadius3D(_x, _y, _z, 1000))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.broadcastPacket(new StopMoveInVehicle(player, _boatId));
		player.setVehicle(null);
		player.setInVehiclePosition(null);
		client.sendPacket(ActionFailed.STATIC_PACKET);
		player.broadcastPacket(new GetOffVehicle(player.getObjectId(), _boatId, _x, _y, _z));
		player.setXYZ(_x, _y, _z);
		player.setInsideZone(ZoneId.PEACE, false);
		player.revalidateZone(true);
	}
}
