package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

/**
 * @author Zoey76
 */
public class RequestHennaRemove implements IClientIncomingPacket
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
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("HennaRemove"))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		Henna henna;
		boolean found = false;
		for (int i = 1; i <= 4; i++)
		{
			henna = player.getHenna(i);
			if ((henna != null) && (henna.getDyeId() == _symbolId))
			{
				if (player.getAdena() >= henna.getCancelFee())
				{
					player.removeHenna(i);
					player.updateSymbolSealSkills();
				}
				else
				{
					player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
					client.sendPacket(ActionFailed.STATIC_PACKET);
				}
				found = true;
				break;
			}
		}
		// TODO: Test.
		if (!found)
		{
			LOGGER.warning(getClass().getSimpleName() + ": Player " + player + " requested Henna Draw remove without any henna.");
			client.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}
}
