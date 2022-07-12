package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: (c) ddd d: dx d: dy d: dz
 * @author -Wooden-
 */
public class MoveWithDelta implements IClientIncomingPacket
{
	@SuppressWarnings("unused")
	private int _dx;
	@SuppressWarnings("unused")
	private int _dy;
	@SuppressWarnings("unused")
	private int _dz;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_dx = packet.readD();
		_dy = packet.readD();
		_dz = packet.readD();
		return false;
	}
	
	@Override
	public void run(GameClient client)
	{
		// TODO this
	}
}
