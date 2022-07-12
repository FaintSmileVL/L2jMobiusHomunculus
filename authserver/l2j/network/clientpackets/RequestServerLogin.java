package l2j.network.clientpackets;

import l2j.AuthConfig;
import network.IIncomingPacket;
import network.PacketReader;
import l2j.LoginController;
import l2j.LoginServer;
import l2j.SessionKey;
import l2j.network.LoginClient;
import l2j.network.gameserverpackets.ServerStatus;
import l2j.network.serverpackets.LoginFail.LoginFailReason;
import l2j.network.serverpackets.PlayFail.PlayFailReason;
import l2j.network.serverpackets.PlayOk;

/**
 * <pre>
 * Format is ddc
 * d: first part of session id
 * d: second part of session id
 * c: server ID
 * </pre>
 */
public class RequestServerLogin implements IIncomingPacket<LoginClient>
{
	private int _skey1;
	private int _skey2;
	private int _serverId;
	
	@Override
	public boolean read(LoginClient client, PacketReader packet)
	{
		if (packet.getReadableBytes() >= 9)
		{
			_skey1 = packet.readD();
			_skey2 = packet.readD();
			_serverId = packet.readC();
			return true;
		}
		return false;
	}
	
	@Override
	public void run(LoginClient client)
	{
		final SessionKey sk = client.getSessionKey();
		
		// if we didnt showed the license we cant check these values
		if (!AuthConfig.SHOW_LICENCE || sk.checkLoginPair(_skey1, _skey2))
		{
			if ((LoginServer.getInstance().getStatus() == ServerStatus.STATUS_DOWN) || ((LoginServer.getInstance().getStatus() == ServerStatus.STATUS_GM_ONLY) && (client.getAccessLevel() < 1)))
			{
				client.close(LoginFailReason.REASON_ACCESS_FAILED);
			}
			else if (LoginController.getInstance().isLoginPossible(client, _serverId))
			{
				client.setJoinedGS(true);
				client.sendPacket(new PlayOk(sk));
			}
			else
			{
				client.close(PlayFailReason.REASON_SERVER_OVERLOADED);
			}
		}
		else
		{
			client.close(LoginFailReason.REASON_ACCESS_FAILED);
		}
	}
}
