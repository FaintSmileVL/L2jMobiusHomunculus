package org.l2jmobius.gameserver.network.clientpackets.ceremonyofchaos;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.CeremonyOfChaosState;
import org.l2jmobius.gameserver.instancemanager.CeremonyOfChaosManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos.ExCuriousHouseState;

/**
 * @author Sdw
 */
public class RequestJoinCuriousHouse implements IClientIncomingPacket
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
		
		if (CeremonyOfChaosManager.getInstance().registerPlayer(player))
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOW_ON_THE_WAITING_LIST_YOU_WILL_AUTOMATICALLY_BE_TELEPORTED_WHEN_THE_TOURNAMENT_STARTS_AND_WILL_BE_REMOVED_FROM_THE_WAITING_LIST_IF_YOU_LOG_OUT_IF_YOU_CANCEL_REGISTRATION_WITHIN_THE_LAST_MINUTE_OF_ENTERING_THE_ARENA_AFTER_SIGNING_UP_30_TIMES_OR_MORE_OR_FORFEIT_AFTER_ENTERING_THE_ARENA_30_TIMES_OR_MORE_DURING_A_CYCLE_YOU_BECOME_INELIGIBLE_FOR_PARTICIPATION_IN_THE_CEREMONY_OF_CHAOS_UNTIL_THE_NEXT_CYCLE_ALL_THE_BUFFS_EXCEPT_THE_VITALITY_BUFF_WILL_BE_REMOVED_ONCE_YOU_ENTER_THE_ARENAS);
			player.sendPacket(SystemMessageId.EXCEPT_THE_VITALITY_BUFF_ALL_BUFFS_INCLUDING_ART_OF_SEDUCTION_WILL_BE_DELETED);
			player.sendPacket(ExCuriousHouseState.PREPARE_PACKET);
		}
		else
		{
			player.sendPacket(SystemMessageId.THERE_ARE_TOO_MANY_CHALLENGERS_YOU_CANNOT_PARTICIPATE_NOW);
		}
	}
}
