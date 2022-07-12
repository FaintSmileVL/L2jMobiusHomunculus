package l2j.network.serverpackets;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class LoginOtpFail implements IOutgoingPacket
{
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.LOGIN_OPT_FAIL.writeId(packet);
		return true;
	}
}
