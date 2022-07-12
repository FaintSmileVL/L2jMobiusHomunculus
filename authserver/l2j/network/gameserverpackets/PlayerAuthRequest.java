package l2j.network.gameserverpackets;

import network.BaseRecievePacket;
import l2j.GameServerThread;
import l2j.LoginController;
import l2j.SessionKey;
import l2j.network.loginserverpackets.PlayerAuthResponse;

/**
 * @author -Wooden-
 */
public class PlayerAuthRequest extends BaseRecievePacket
{
	/**
	 * @param decrypt
	 * @param server
	 */
	public PlayerAuthRequest(byte[] decrypt, GameServerThread server)
	{
		super(decrypt);
		final String account = readS();
		final int playKey1 = readD();
		final int playKey2 = readD();
		final int loginKey1 = readD();
		final int loginKey2 = readD();
		final SessionKey sessionKey = new SessionKey(loginKey1, loginKey2, playKey1, playKey2);
		PlayerAuthResponse authResponse;
		final SessionKey key = LoginController.getInstance().getKeyForAccount(account);
		if ((key != null) && key.equals(sessionKey))
		{
			LoginController.getInstance().removeAuthedLoginClient(account);
			authResponse = new PlayerAuthResponse(account, true);
		}
		else
		{
			authResponse = new PlayerAuthResponse(account, false);
		}
		server.sendPacket(authResponse);
	}
}
