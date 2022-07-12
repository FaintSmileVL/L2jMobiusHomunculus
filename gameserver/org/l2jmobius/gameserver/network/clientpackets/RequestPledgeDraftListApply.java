package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.ClanEntryManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.entry.PledgeWaitingInfo;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Sdw
 */
public class RequestPledgeDraftListApply implements IClientIncomingPacket
{
	private int _applyType;
	private int _karma;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_applyType = packet.readD();
		_karma = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getClan() != null))
		{
			return;
		}
		
		if (player.getClan() != null)
		{
			client.sendPacket(SystemMessageId.ONLY_THE_CLAN_LEADER_OR_SOMEONE_WITH_RANK_MANAGEMENT_AUTHORITY_MAY_REGISTER_THE_CLAN);
			return;
		}
		
		switch (_applyType)
		{
			case 0: // remove
			{
				if (ClanEntryManager.getInstance().removeFromWaitingList(player.getObjectId()))
				{
					client.sendPacket(SystemMessageId.ENTRY_APPLICATION_CANCELLED_YOU_MAY_APPLY_TO_A_NEW_CLAN_AFTER_5_MINUTES);
				}
				break;
			}
			case 1: // add
			{
				final PledgeWaitingInfo pledgeDraftList = new PledgeWaitingInfo(player.getObjectId(), player.getLevel(), _karma, player.getClassId().getId(), player.getName());
				if (ClanEntryManager.getInstance().addToWaitingList(player.getObjectId(), pledgeDraftList))
				{
					client.sendPacket(SystemMessageId.YOU_HAVE_JOINED_THE_WAITING_LIST_IF_YOU_DO_NOT_JOIN_ANY_CLAN_IN_30_DAYS_YOUR_CHARACTER_WILL_BE_REMOVED_FROM_THE_LIST_IF_EXIT_WAITING_LIST_IS_USED_YOU_WILL_NOT_BE_ABLE_TO_JOIN_THE_WAITING_LIST_FOR_5_MINUTES);
				}
				else
				{
					final SystemMessage sm = new SystemMessage(SystemMessageId.YOU_MAY_APPLY_FOR_ENTRY_AFTER_S1_MINUTE_S_DUE_TO_CANCELLING_YOUR_APPLICATION);
					sm.addLong(ClanEntryManager.getInstance().getPlayerLockTime(player.getObjectId()));
					client.sendPacket(sm);
				}
				break;
			}
		}
	}
}
