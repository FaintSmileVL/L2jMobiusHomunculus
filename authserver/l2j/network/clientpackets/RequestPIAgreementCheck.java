package l2j.network.clientpackets;

import l2j.AuthConfig;
import network.IIncomingPacket;
import network.PacketReader;
import l2j.network.LoginClient;
import l2j.network.serverpackets.PIAgreementCheck;

/**
 * @author UnAfraid
 */
public class RequestPIAgreementCheck implements IIncomingPacket<LoginClient>
{
	private int _accountId;
	
	@Override
	public boolean read(LoginClient client, PacketReader packet)
	{
		_accountId = packet.readD();
		final byte[] padding0 = new byte[3];
		final byte[] checksum = new byte[4];
		final byte[] padding1 = new byte[12];
		packet.readB(padding0, 0, padding0.length);
		packet.readB(checksum, 0, checksum.length);
		packet.readB(padding1, 0, padding1.length);
		return true;
	}
	
	@Override
	public void run(LoginClient client)
	{
		client.sendPacket(new PIAgreementCheck(_accountId, AuthConfig.SHOW_PI_AGREEMENT ? 0x01 : 0x00));
	}
}
