package l2j.network.gameserverpackets;

import java.util.logging.Logger;

import network.BaseRecievePacket;
import l2j.GameServerThread;

/**
 * @author -Wooden-
 */
public class PlayerLogout extends BaseRecievePacket
{
	protected static final Logger LOGGER = Logger.getLogger(PlayerLogout.class.getName());
	
	/**
	 * @param decrypt
	 * @param server
	 */
	public PlayerLogout(byte[] decrypt, GameServerThread server)
	{
		super(decrypt);
		final String account = readS();
		server.removeAccountOnGameServer(account);
	}
}
