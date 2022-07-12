package org.l2jmobius.gameserver.network.loginserverpackets.game;

import network.BaseSendablePacket;

/**
 * @author UnAfraid
 */
public class ChangePassword extends BaseSendablePacket
{
	public ChangePassword(String accountName, String characterName, String oldPass, String newPass)
	{
		writeC(0x0B);
		writeS(accountName);
		writeS(characterName);
		writeS(oldPass);
		writeS(newPass);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}