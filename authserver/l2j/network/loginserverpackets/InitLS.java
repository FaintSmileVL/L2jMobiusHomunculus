package l2j.network.loginserverpackets;

import network.BaseSendablePacket;
import l2j.LoginServer;

/**
 * @author -Wooden-
 */
public class InitLS extends BaseSendablePacket
{
	// ID 0x00
	// format
	// d proto rev
	// d key size
	// b key
	
	public InitLS(byte[] publickey)
	{
		writeC(0x00);
		writeD(LoginServer.PROTOCOL_REV);
		writeD(publickey.length);
		writeB(publickey);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}
