package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.AdminData;
import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.instancemanager.PetitionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.CreatureSay;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * <p>
 * Format: (c) d
 * <ul>
 * <li>d: Unknown</li>
 * </ul>
 * </p>
 * @author -Wooden-, TempyIncursion
 */
public class RequestPetitionCancel implements IClientIncomingPacket
{
	// private int _unknown;
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		// _unknown = packet.readD(); This is pretty much a trigger packet.
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
		
		if (PetitionManager.getInstance().isPlayerInConsultation(player))
		{
			if (player.isGM())
			{
				PetitionManager.getInstance().endActivePetition(player);
			}
			else
			{
				player.sendPacket(SystemMessageId.YOUR_PETITION_IS_BEING_PROCESSED);
			}
		}
		else if (PetitionManager.getInstance().isPlayerPetitionPending(player))
		{
			if (PetitionManager.getInstance().cancelActivePetition(player))
			{
				final int numRemaining = Config.MAX_PETITIONS_PER_PLAYER - PetitionManager.getInstance().getPlayerTotalPetitionCount(player);
				final SystemMessage sm = new SystemMessage(SystemMessageId.THE_PETITION_WAS_CANCELED_YOU_MAY_SUBMIT_S1_MORE_PETITION_S_TODAY);
				sm.addString(String.valueOf(numRemaining));
				player.sendPacket(sm);
				
				// Notify all GMs that the player's pending petition has been cancelled.
				final String msgContent = player.getName() + " has canceled a pending petition.";
				AdminData.getInstance().broadcastToGMs(new CreatureSay(player, ChatType.HERO_VOICE, "Petition System", msgContent));
			}
			else
			{
				player.sendPacket(SystemMessageId.FAILED_TO_CANCEL_PETITION_PLEASE_TRY_AGAIN_LATER);
			}
		}
		else
		{
			player.sendPacket(SystemMessageId.YOU_HAVE_NOT_SUBMITTED_A_PETITION);
		}
	}
}
