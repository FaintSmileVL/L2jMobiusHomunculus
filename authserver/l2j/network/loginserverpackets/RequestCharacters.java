package l2j.network.loginserverpackets;

import network.BaseSendablePacket;

/**
 * @author mrTJO
 */
public class RequestCharacters extends BaseSendablePacket
{
	public RequestCharacters(String account)
	{
		writeC(0x05);
		writeS(account);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}
