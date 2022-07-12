package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.AllyCrest;

/**
 * @version $Revision: 1.3.4.4 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestAllyCrest implements IClientIncomingPacket
{
	private int _crestId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_crestId = packet.readD();
		packet.readD(); // Ally ID
		packet.readD(); // Server ID
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		client.sendPacket(new AllyCrest(_crestId));
	}
}
