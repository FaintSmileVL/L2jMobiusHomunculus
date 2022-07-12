package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.StartRotation;

/**
 * @version $Revision: 1.1.4.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class StartRotating implements IClientIncomingPacket
{
	private int _degree;
	private int _side;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_degree = packet.readD();
		_side = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (!Config.ENABLE_KEYBOARD_MOVEMENT)
		{
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (player.isInAirShip() && player.getAirShip().isCaptain(player))
		{
			player.getAirShip().broadcastPacket(new StartRotation(player.getAirShip().getObjectId(), _degree, _side, 0));
		}
		else
		{
			player.broadcastPacket(new StartRotation(player.getObjectId(), _degree, _side, 0));
		}
	}
}
