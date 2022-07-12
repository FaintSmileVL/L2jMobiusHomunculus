package org.l2jmobius.gameserver.network.loginserverpackets.game;

import network.BaseSendablePacket;
import org.l2jmobius.gameserver.LoginServerThread.SessionKey;

/**
 * @author -Wooden-
 */
public class PlayerAuthRequest extends BaseSendablePacket
{
	public PlayerAuthRequest(String account, SessionKey key)
	{
		writeC(0x05);
		writeS(account);
		writeD(key.playOkID1);
		writeD(key.playOkID2);
		writeD(key.loginOkID1);
		writeD(key.loginOkID2);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}