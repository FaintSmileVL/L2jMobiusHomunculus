package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.FortManager;
import org.l2jmobius.gameserver.model.siege.Fort;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.ExShowFortressMapInfo;

/**
 * @author KenM
 */
public class RequestFortressMapInfo implements IClientIncomingPacket
{
	private int _fortressId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_fortressId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final Fort fort = FortManager.getInstance().getFortById(_fortressId);
		if (fort == null)
		{
			LOGGER.warning("Fort is not found with id (" + _fortressId + ") in all forts with size of (" + FortManager.getInstance().getForts().size() + ") called by player (" + client.getPlayer() + ")");
			if (client.getPlayer() == null)
			{
				return;
			}
			
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		client.sendPacket(new ExShowFortressMapInfo(fort));
	}
}
