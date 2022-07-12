package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.LoginServerThread;
import org.l2jmobius.gameserver.LoginServerThread.SessionKey;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @version $Revision: 1.9.2.3.2.4 $ $Date: 2005/03/27 15:29:30 $
 */
public class AuthLogin implements IClientIncomingPacket
{
	// loginName + keys must match what the loginserver used.
	private String _loginName;
	/*
	 * private final long _key1; private final long _key2; private final long _key3; private final long _key4;
	 */
	private int _playKey1;
	private int _playKey2;
	private int _loginKey1;
	private int _loginKey2;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_loginName = packet.readS().toLowerCase();
		_playKey2 = packet.readD();
		_playKey1 = packet.readD();
		_loginKey1 = packet.readD();
		_loginKey2 = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (_loginName.isEmpty() || !client.isProtocolOk())
		{
			client.closeNow();
			return;
		}
		
		final SessionKey key = new SessionKey(_loginKey1, _loginKey2, _playKey1, _playKey2);
		
		// avoid potential exploits
		if (client.getAccountName() == null)
		{
			// Preventing duplicate login in case client login server socket was disconnected or this packet was not sent yet
			if (LoginServerThread.getInstance().addGameServerLogin(_loginName, client))
			{
				client.setAccountName(_loginName);
				LoginServerThread.getInstance().addWaitingClientAndSendRequest(_loginName, client, key);
			}
			else
			{
				client.close(null);
			}
		}
	}
}
