package org.l2jmobius.gameserver.network.loginserverpackets.game;

import java.util.List;

import network.BaseSendablePacket;

/**
 * @author -Wooden-
 */
public class PlayerInGame extends BaseSendablePacket
{
	public PlayerInGame(String player)
	{
		writeC(0x02);
		writeH(1);
		writeS(player);
	}
	
	public PlayerInGame(List<String> players)
	{
		writeC(0x02);
		writeH(players.size());
		for (String pc : players)
		{
			writeS(pc);
		}
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}