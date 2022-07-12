package l2j.network.clientpackets;

import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;

import l2j.AuthConfig;
import network.IIncomingPacket;
import network.PacketReader;
import l2j.GameServerInfo;
import l2j.LoginController;
import l2j.LoginController.AuthLoginResult;
import l2j.data.AccountInfo;
import l2j.network.ConnectionState;
import l2j.network.LoginClient;
import l2j.network.serverpackets.AccountKicked;
import l2j.network.serverpackets.AccountKicked.AccountKickedReason;
import l2j.network.serverpackets.LoginFail.LoginFailReason;
import l2j.network.serverpackets.LoginOk;
import l2j.network.serverpackets.ServerList;

/**
 * Format: x 0 (a leading null) x: the rsa encrypted block with the login an password.
 */
public class RequestAuthLogin implements IIncomingPacket<LoginClient>
{
	private static final Logger LOGGER = Logger.getLogger(RequestAuthLogin.class.getName());
	
	private final byte[] _raw1 = new byte[128];
	private final byte[] _raw2 = new byte[128];
	private boolean _newAuthMethod = false;
	
	@Override
	public boolean read(LoginClient client, PacketReader packet)
	{
		if (packet.getReadableBytes() >= 256)
		{
			_newAuthMethod = true;
			packet.readB(_raw1, 0, _raw1.length);
			packet.readB(_raw2, 0, _raw2.length);
			return true;
		}
		else if (packet.getReadableBytes() >= 128)
		{
			packet.readB(_raw1, 0, _raw1.length);
			return true;
		}
		return false;
	}
	
	@Override
	public void run(LoginClient client)
	{
		if (AuthConfig.ENABLE_CMD_LINE_LOGIN && AuthConfig.ONLY_CMD_LINE_LOGIN)
		{
			return;
		}
		
		final byte[] decrypted = new byte[_newAuthMethod ? 256 : 128];
		try
		{
			final Cipher rsaCipher = Cipher.getInstance("RSA/ECB/nopadding");
			rsaCipher.init(Cipher.DECRYPT_MODE, client.getScrambledKeyPair().getPrivateKey());
			rsaCipher.doFinal(_raw1, 0, 128, decrypted, 0);
			if (_newAuthMethod)
			{
				rsaCipher.doFinal(_raw2, 0, 128, decrypted, 128);
			}
		}
		catch (GeneralSecurityException e)
		{
			LOGGER.log(Level.INFO, "", e);
			return;
		}
		
		final String user;
		final String password;
		try
		{
			if (_newAuthMethod)
			{
				user = new String(decrypted, 0x4E, 50).trim() + new String(decrypted, 0xCE, 14).trim();
				password = new String(decrypted, 0xDC, 16).trim();
			}
			else
			{
				user = new String(decrypted, 0x5E, 14).trim();
				password = new String(decrypted, 0x6C, 16).trim();
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "", e);
			return;
		}
		
		final InetAddress clientAddr = client.getConnectionAddress();
		final LoginController lc = LoginController.getInstance();
		final AccountInfo info = lc.retriveAccountInfo(clientAddr, user, password);
		if (info == null)
		{
			// user or pass wrong
			// client.close(LoginFailReason.REASON_SYSTEM_ERROR);
			// above message crashes client
			// REASON_ACCOUNT_INFO_INCORRECT_CONTACT_SUPPORT seems ok as well
			client.close(LoginFailReason.REASON_ACCESS_FAILED);
			return;
		}
		
		final AuthLoginResult result = lc.tryCheckinAccount(client, clientAddr, info);
		switch (result)
		{
			case AUTH_SUCCESS:
			{
				client.setAccount(info.getLogin());
				client.setConnectionState(ConnectionState.AUTHED_LOGIN);
				client.setSessionKey(lc.assignSessionKeyToClient(info.getLogin(), client));
				lc.getCharactersOnAccount(info.getLogin());
				if (AuthConfig.SHOW_LICENCE)
				{
					client.sendPacket(new LoginOk(client.getSessionKey()));
				}
				else
				{
					client.sendPacket(new ServerList(client));
				}
				break;
			}
			case INVALID_PASSWORD:
			{
				client.close(LoginFailReason.REASON_USER_OR_PASS_WRONG);
				break;
			}
			case ACCOUNT_BANNED:
			{
				client.close(new AccountKicked(AccountKickedReason.REASON_PERMANENTLY_BANNED));
				return;
			}
			case ALREADY_ON_LS:
			{
				final LoginClient oldClient = lc.getAuthedClient(info.getLogin());
				if (oldClient != null)
				{
					// kick the other client
					oldClient.close(LoginFailReason.REASON_ACCOUNT_IN_USE);
					lc.removeAuthedLoginClient(info.getLogin());
				}
				// kick also current client
				client.close(LoginFailReason.REASON_ACCOUNT_IN_USE);
				break;
			}
			case ALREADY_ON_GS:
			{
				final GameServerInfo gsi = lc.getAccountOnGameServer(info.getLogin());
				if (gsi != null)
				{
					client.close(LoginFailReason.REASON_ACCOUNT_IN_USE);
					// kick from there
					if (gsi.isAuthed())
					{
						gsi.getGameServerThread().kickPlayer(info.getLogin());
					}
				}
				break;
			}
		}
	}
}
