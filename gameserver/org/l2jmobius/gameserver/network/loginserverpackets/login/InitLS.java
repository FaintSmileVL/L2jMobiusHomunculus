package org.l2jmobius.gameserver.network.loginserverpackets.login;

import network.BaseRecievePacket;

public class InitLS extends BaseRecievePacket
{
	private final int _rev;
	private final byte[] _key;
	
	public int getRevision()
	{
		return _rev;
	}
	
	public byte[] getRSAKey()
	{
		return _key;
	}
	
	/**
	 * @param decrypt
	 */
	public InitLS(byte[] decrypt)
	{
		super(decrypt);
		_rev = readD();
		final int size = readD();
		_key = readB(size);
	}
}