package org.l2jmobius.gameserver.network.loginserverpackets.game;

import network.BaseSendablePacket;

/**
 * @author -Wooden-
 */
public class ChangeAccessLevel extends BaseSendablePacket
{
	public ChangeAccessLevel(String player, int access)
	{
		writeC(0x04);
		writeD(access);
		writeS(player);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}