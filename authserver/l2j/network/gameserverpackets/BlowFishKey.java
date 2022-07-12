package l2j.network.gameserverpackets;

import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;

import network.BaseRecievePacket;
import util.crypt.NewCrypt;
import l2j.GameServerThread;
import l2j.network.GameServerPacketHandler.GameServerState;

/**
 * @author -Wooden-
 */
public class BlowFishKey extends BaseRecievePacket
{
	protected static final Logger LOGGER = Logger.getLogger(BlowFishKey.class.getName());
	
	/**
	 * @param decrypt
	 * @param server
	 */
	public BlowFishKey(byte[] decrypt, GameServerThread server)
	{
		super(decrypt);
		final int size = readD();
		final byte[] tempKey = readB(size);
		try
		{
			byte[] tempDecryptKey;
			final Cipher rsaCipher = Cipher.getInstance("RSA/ECB/nopadding");
			rsaCipher.init(Cipher.DECRYPT_MODE, server.getPrivateKey());
			tempDecryptKey = rsaCipher.doFinal(tempKey);
			// there are nulls before the key we must remove them
			int i = 0;
			final int len = tempDecryptKey.length;
			for (; i < len; i++)
			{
				if (tempDecryptKey[i] != 0)
				{
					break;
				}
			}
			final byte[] key = new byte[len - i];
			System.arraycopy(tempDecryptKey, i, key, 0, len - i);
			server.SetBlowFish(new NewCrypt(key));
			server.setLoginConnectionState(GameServerState.BF_CONNECTED);
		}
		catch (GeneralSecurityException e)
		{
			LOGGER.log(Level.SEVERE, "Error While decrypting blowfish key (RSA): " + e.getMessage(), e);
		}
	}
}
