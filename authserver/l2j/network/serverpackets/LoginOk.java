package l2j.network.serverpackets;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.SessionKey;
import l2j.network.OutgoingPackets;

/**
 * <pre>
 * Format: dddddddd
 * f: the session key
 * d: ?
 * d: ?
 * d: ?
 * d: ?
 * d: ?
 * d: ?
 * b: 16 bytes - unknown
 * </pre>
 */
public class LoginOk implements IOutgoingPacket
{
	private final int _loginOk1;
	private final int _loginOk2;
	
	public LoginOk(SessionKey sessionKey)
	{
		_loginOk1 = sessionKey.loginOkID1;
		_loginOk2 = sessionKey.loginOkID2;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.LOGIN_OK.writeId(packet);
		packet.writeD(_loginOk1);
		packet.writeD(_loginOk2);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x000003ea);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeB(new byte[16]);
		return true;
	}
}
