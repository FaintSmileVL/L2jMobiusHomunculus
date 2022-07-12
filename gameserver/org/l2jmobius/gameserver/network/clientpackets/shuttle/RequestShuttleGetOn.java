package org.l2jmobius.gameserver.network.clientpackets.shuttle;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.instance.ShuttleInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;

/**
 * @author UnAfraid
 */
public class RequestShuttleGetOn implements IClientIncomingPacket
{
	private int _x;
	private int _y;
	private int _z;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readD(); // charId
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
		
		// TODO: better way?
		for (ShuttleInstance shuttle : World.getInstance().getVisibleObjects(player, ShuttleInstance.class))
		{
			if (shuttle.calculateDistance3D(player) < 1000)
			{
				shuttle.addPassenger(player);
				player.getInVehiclePosition().setXYZ(_x, _y, _z);
				break;
			}
			LOGGER.info(getClass().getSimpleName() + ": range between char and shuttle: " + shuttle.calculateDistance3D(player));
		}
	}
}
