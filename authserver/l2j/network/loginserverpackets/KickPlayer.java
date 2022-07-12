package l2j.network.loginserverpackets;

import network.BaseSendablePacket;

/**
 * @author -Wooden-
 */
public class KickPlayer extends BaseSendablePacket
{
	public KickPlayer(String account)
	{
		writeC(0x04);
		writeS(account);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}
