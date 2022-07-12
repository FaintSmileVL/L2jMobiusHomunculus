package org.l2jmobius.gameserver.network.loginserverpackets.game;

import network.BaseSendablePacket;

/**
 * @author -Wooden-
 */
public class PlayerLogout extends BaseSendablePacket
{
	public PlayerLogout(String player)
	{
		writeC(0x03);
		writeS(player);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}