package l2j.network.gameserverpackets;

import java.util.logging.Logger;

import network.BaseRecievePacket;
import l2j.LoginController;

/**
 * @author mrTJO
 */
public class PlayerTracert extends BaseRecievePacket
{
	protected static final Logger LOGGER = Logger.getLogger(PlayerTracert.class.getName());
	
	/**
	 * @param decrypt
	 */
	public PlayerTracert(byte[] decrypt)
	{
		super(decrypt);
		final String account = readS();
		final String pcIp = readS();
		final String hop1 = readS();
		final String hop2 = readS();
		final String hop3 = readS();
		final String hop4 = readS();
		LoginController.getInstance().setAccountLastTracert(account, pcIp, hop1, hop2, hop3, hop4);
	}
}
