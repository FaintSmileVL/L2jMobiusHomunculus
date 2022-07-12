package l2j.network.gameserverpackets;

import java.util.logging.Logger;

import network.BaseRecievePacket;
import l2j.GameServerThread;
import l2j.LoginController;

/**
 * @author -Wooden-
 */
public class ChangeAccessLevel extends BaseRecievePacket
{
	protected static final Logger LOGGER = Logger.getLogger(ChangeAccessLevel.class.getName());
	
	/**
	 * @param decrypt
	 * @param server
	 */
	public ChangeAccessLevel(byte[] decrypt, GameServerThread server)
	{
		super(decrypt);
		final int level = readD();
		final String account = readS();
		LoginController.getInstance().setAccountAccessLevel(account, level);
		LOGGER.info("Changed " + account + " access level to " + level);
	}
}
