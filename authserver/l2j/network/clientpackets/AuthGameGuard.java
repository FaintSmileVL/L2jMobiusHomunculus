package l2j.network.clientpackets;

import network.IIncomingPacket;
import network.PacketReader;
import l2j.network.ConnectionState;
import l2j.network.LoginClient;
import l2j.network.serverpackets.GGAuth;
import l2j.network.serverpackets.LoginFail.LoginFailReason;

/**
 * Format: ddddd
 * @author -Wooden-
 */
public class AuthGameGuard implements IIncomingPacket<LoginClient>
{
	private int _sessionId;
	
	@SuppressWarnings("unused")
	private int _data1;
	@SuppressWarnings("unused")
	private int _data2;
	@SuppressWarnings("unused")
	private int _data3;
	@SuppressWarnings("unused")
	private int _data4;
	
	@Override
	public boolean read(LoginClient client, PacketReader packet)
	{
		if (packet.getReadableBytes() >= 20)
		{
			_sessionId = packet.readD();
			_data1 = packet.readD();
			_data2 = packet.readD();
			_data3 = packet.readD();
			_data4 = packet.readD();
			return true;
		}
		return false;
	}
	
	@Override
	public void run(LoginClient client)
	{
		if (_sessionId == client.getSessionId())
		{
			client.setConnectionState(ConnectionState.AUTHED_GG);
			client.sendPacket(new GGAuth(client.getSessionId()));
		}
		else
		{
			client.close(LoginFailReason.REASON_ACCESS_FAILED);
		}
	}
}
