package org.l2jmobius.gameserver.network.clientpackets.ceremonyofchaos;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.CeremonyOfChaosState;
import org.l2jmobius.gameserver.instancemanager.CeremonyOfChaosManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Sdw
 */
public class RequestCuriousHouseHtml implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		// Nothing to read
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
		
		if (CeremonyOfChaosManager.getInstance().getState() != CeremonyOfChaosState.REGISTRATION)
		{
			return;
		}
		else if (CeremonyOfChaosManager.getInstance().isRegistered(player))
		{
			player.sendPacket(SystemMessageId.YOU_ARE_ON_THE_WAITING_LIST_FOR_THE_CEREMONY_OF_CHAOS);
			return;
		}
		
		if (CeremonyOfChaosManager.getInstance().canRegister(player, true))
		{
			final NpcHtmlMessage message = new NpcHtmlMessage(0);
			message.setFile(player, "data/html/CeremonyOfChaos/invite.htm");
			player.sendPacket(message);
		}
	}
}
