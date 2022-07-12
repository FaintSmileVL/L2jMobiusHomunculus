package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.StopRotation;

/**
 * @version $Revision: 1.1.4.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class FinishRotating implements IClientIncomingPacket
{
	private int _degree;
	@SuppressWarnings("unused")
	private int _unknown;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_degree = packet.readD();
		_unknown = packet.readD();
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
		
		StopRotation sr;
		if (player.isInAirShip() && player.getAirShip().isCaptain(player))
		{
			player.getAirShip().setHeading(_degree);
			sr = new StopRotation(player.getAirShip().getObjectId(), _degree, 0);
			player.getAirShip().broadcastPacket(sr);
		}
		else
		{
			sr = new StopRotation(player.getObjectId(), _degree, 0);
			player.broadcastPacket(sr);
		}
	}
}
