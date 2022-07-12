package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.HennaData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.HennaItemDrawInfo;

/**
 * @author Zoey76
 */
public class RequestHennaItemInfo implements IClientIncomingPacket
{
	private int _symbolId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_symbolId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		final Henna henna = HennaData.getInstance().getHenna(_symbolId);
		if (henna == null)
		{
			if (_symbolId != 0)
			{
				LOGGER.warning(getClass().getSimpleName() + ": Invalid Henna Id: " + _symbolId + " from player " + player);
			}
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		client.sendPacket(new HennaItemDrawInfo(henna, player));
	}
}
