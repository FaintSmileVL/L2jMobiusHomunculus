package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: (c) dddd d: dx d: dy d: dz d: AirShip id ??
 * @author -Wooden-
 */
public class ExGetOnAirShip implements IClientIncomingPacket
{
	private int _x;
	private int _y;
	private int _z;
	private int _shipId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_x = packet.readD();
		_y = packet.readD();
		_z = packet.readD();
		_shipId = packet.readD();
		return false;
	}
	
	@Override
	public void run(GameClient client)
	{
		LOGGER.info("[T1:ExGetOnAirShip] x: " + _x);
		LOGGER.info("[T1:ExGetOnAirShip] y: " + _y);
		LOGGER.info("[T1:ExGetOnAirShip] z: " + _z);
		LOGGER.info("[T1:ExGetOnAirShip] ship ID: " + _shipId);
	}
}
