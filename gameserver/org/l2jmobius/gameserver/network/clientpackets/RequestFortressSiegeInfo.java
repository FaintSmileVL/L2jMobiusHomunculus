package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.FortManager;
import org.l2jmobius.gameserver.model.siege.Fort;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExShowFortressSiegeInfo;

/**
 * @author KenM
 */
public class RequestFortressSiegeInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		for (Fort fort : FortManager.getInstance().getForts())
		{
			if ((fort != null) && fort.getSiege().isInProgress())
			{
				client.sendPacket(new ExShowFortressSiegeInfo(fort));
			}
		}
	}
}
