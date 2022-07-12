package l2j.network.gameserverpackets;

import network.BaseRecievePacket;
import l2j.GameServerThread;

/**
 * @author -Wooden-
 */
public class PlayerInGame extends BaseRecievePacket
{
	/**
	 * @param decrypt
	 * @param server
	 */
	public PlayerInGame(byte[] decrypt, GameServerThread server)
	{
		super(decrypt);
		final int size = readH();
		for (int i = 0; i < size; i++)
		{
			final String account = readS();
			server.addAccountOnGameServer(account);
		}
	}
}
