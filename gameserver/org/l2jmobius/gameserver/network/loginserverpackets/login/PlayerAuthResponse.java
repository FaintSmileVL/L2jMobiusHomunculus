package org.l2jmobius.gameserver.network.loginserverpackets.login;

import network.BaseRecievePacket;

/**
 * @author -Wooden-
 */
public class PlayerAuthResponse extends BaseRecievePacket
{
	private final String _account;
	private final boolean _authed;
	
	/**
	 * @param decrypt
	 */
	public PlayerAuthResponse(byte[] decrypt)
	{
		super(decrypt);
		
		_account = readS();
		_authed = readC() != 0;
	}
	
	/**
	 * @return Returns the account.
	 */
	public String getAccount()
	{
		return _account;
	}
	
	/**
	 * @return Returns the authed state.
	 */
	public boolean isAuthed()
	{
		return _authed;
	}
}