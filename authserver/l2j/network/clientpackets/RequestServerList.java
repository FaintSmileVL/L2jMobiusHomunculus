package l2j.network.clientpackets;

import network.IIncomingPacket;
import network.PacketReader;
import l2j.network.LoginClient;
import l2j.network.serverpackets.LoginFail.LoginFailReason;
import l2j.network.serverpackets.ServerList;

/**
 * <pre>
 * Format: ddc
 * d: fist part of session id
 * d: second part of session id
 * c: ?
 * </pre>
 */
public class RequestServerList implements IIncomingPacket<LoginClient>
{
	private int _skey1;
	private int _skey2;
	@SuppressWarnings("unused")
	private int _data3;
	
	@Override
	public boolean read(LoginClient client, PacketReader packet)
	{
		if (packet.getReadableBytes() >= 8)
		{
			_skey1 = packet.readD(); // loginOk 1
			_skey2 = packet.readD(); // loginOk 2
			return true;
		}
		return false;
	}
	
	@Override
	public void run(LoginClient client)
	{
		if (client.getSessionKey().checkLoginPair(_skey1, _skey2))
		{
			client.sendPacket(new ServerList(client));
		}
		else
		{
			client.close(LoginFailReason.REASON_ACCESS_FAILED);
		}
	}
}
