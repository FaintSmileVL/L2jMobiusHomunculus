package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.taskmanager.AttackStanceTaskManager;

public class RequestStopPledgeWar implements IClientIncomingPacket
{
	private String _pledgeName;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_pledgeName = packet.readS();
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
		final Clan playerClan = player.getClan();
		if (playerClan == null)
		{
			return;
		}
		
		final Clan clan = ClanTable.getInstance().getClanByName(_pledgeName);
		if (clan == null)
		{
			player.sendMessage("No such clan.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!playerClan.isAtWarWith(clan.getId()))
		{
			player.sendMessage("You aren't at war with this clan.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Check if player who does the request has the correct rights to do it
		if (!player.hasClanPrivilege(ClanPrivilege.CL_PLEDGE_WAR))
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		
		for (ClanMember member : playerClan.getMembers())
		{
			if ((member == null) || (member.getPlayerInstance() == null))
			{
				continue;
			}
			if (AttackStanceTaskManager.getInstance().hasAttackStanceTask(member.getPlayerInstance()))
			{
				player.sendPacket(SystemMessageId.A_CEASE_FIRE_DURING_A_CLAN_WAR_CAN_NOT_BE_CALLED_WHILE_MEMBERS_OF_YOUR_CLAN_ARE_ENGAGED_IN_BATTLE);
				return;
			}
		}
		
		// Reduce reputation.
		playerClan.takeReputationScore(5000, true);
		ClanTable.getInstance().deleteClanWars(playerClan.getId(), clan.getId());
		for (PlayerInstance member : playerClan.getOnlineMembers(0))
		{
			member.broadcastUserInfo();
		}
		
		for (PlayerInstance member : clan.getOnlineMembers(0))
		{
			member.broadcastUserInfo();
		}
	}
}
