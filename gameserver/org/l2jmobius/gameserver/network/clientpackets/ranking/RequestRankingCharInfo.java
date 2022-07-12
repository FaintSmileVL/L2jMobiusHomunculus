package org.l2jmobius.gameserver.network.clientpackets.ranking;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.ranking.ExRankingCharInfo;

/**
 * @author JoeAlisson
 */
public class RequestRankingCharInfo implements IClientIncomingPacket
{
	private short _unk;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_unk = packet.readC();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		client.sendPacket(new ExRankingCharInfo(client.getPlayer(), _unk));
	}
}
