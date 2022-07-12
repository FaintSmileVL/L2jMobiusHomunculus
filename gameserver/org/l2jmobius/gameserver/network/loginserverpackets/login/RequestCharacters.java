package org.l2jmobius.gameserver.network.loginserverpackets.login;

import network.BaseRecievePacket;

/**
 * @author mrTJO Thanks to mochitto
 */
public class RequestCharacters extends BaseRecievePacket
{
	private final String _account;
	
	public RequestCharacters(byte[] decrypt)
	{
		super(decrypt);
		_account = readS();
	}
	
	/**
	 * @return Return account name
	 */
	public String getAccount()
	{
		return _account;
	}
}
