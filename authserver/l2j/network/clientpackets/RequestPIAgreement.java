package l2j.network.clientpackets;

import network.IIncomingPacket;
import network.PacketReader;
import l2j.network.LoginClient;
import l2j.network.serverpackets.PIAgreementAck;

/**
 * @author UnAfraid
 */
public class RequestPIAgreement implements IIncomingPacket<LoginClient>
{
	private int _accountId;
	private int _status;
	
	@Override
	public boolean read(LoginClient client, PacketReader packet)
	{
		_accountId = packet.readD();
		_status = packet.readC();
		return true;
	}
	
	@Override
	public void run(LoginClient client)
	{
		client.sendPacket(new PIAgreementAck(_accountId, _status));
	}
}
