package org.l2jmobius.gameserver.network.clientpackets;

import java.util.logging.Logger;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.KeyPacket;

/**
 * @version $Revision: 1.5.2.8.2.8 $ $Date: 2005/04/02 10:43:04 $
 */
public class ProtocolVersion implements IClientIncomingPacket
{
	private static final Logger LOGGER_ACCOUNTING = Logger.getLogger("accounting");
	
	private int _version;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_version = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		// this packet is never encrypted
		if (_version == -2)
		{
			// this is just a ping attempt from the new C2 client
			client.closeNow();
		}
		else if (!Config.PROTOCOL_LIST.contains(_version))
		{
			LOGGER_ACCOUNTING.warning("Wrong protocol version " + _version + ", " + client);
			client.setProtocolOk(false);
			client.close(new KeyPacket(client.enableCrypt(), 0));
		}
		else
		{
			client.sendPacket(new KeyPacket(client.enableCrypt(), 1));
			client.setProtocolVersion(_version);
			client.setProtocolOk(true);
		}
	}
}
