package l2j.network.serverpackets;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class PIAgreementAck implements IOutgoingPacket
{
	private final int _accountId;
	private final int _status;
	
	public PIAgreementAck(int accountId, int status)
	{
		_accountId = accountId;
		_status = status;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PI_AGREEMENT_ACK.writeId(packet);
		packet.writeD(_accountId);
		packet.writeC(_status);
		return true;
	}
}
