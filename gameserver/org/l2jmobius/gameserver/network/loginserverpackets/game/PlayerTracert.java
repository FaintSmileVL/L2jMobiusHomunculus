package org.l2jmobius.gameserver.network.loginserverpackets.game;

import network.BaseSendablePacket;

/**
 * @author mrTJO
 */
public class PlayerTracert extends BaseSendablePacket
{
	public PlayerTracert(String account, String pcIp, String hop1, String hop2, String hop3, String hop4)
	{
		writeC(0x07);
		writeS(account);
		writeS(pcIp);
		writeS(hop1);
		writeS(hop2);
		writeS(hop3);
		writeS(hop4);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}