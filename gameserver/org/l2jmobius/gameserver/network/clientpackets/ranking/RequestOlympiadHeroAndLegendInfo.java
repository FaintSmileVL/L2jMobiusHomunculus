package org.l2jmobius.gameserver.network.clientpackets.ranking;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.ranking.ExOlympiadHeroAndLegendInfo;

/**
 * @author NviX
 */
public class RequestOlympiadHeroAndLegendInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		client.sendPacket(new ExOlympiadHeroAndLegendInfo());
	}
}
