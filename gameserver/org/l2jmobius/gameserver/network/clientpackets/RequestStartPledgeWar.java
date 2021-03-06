package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.enums.ClanWarState;
import org.l2jmobius.gameserver.enums.UserInfoType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.model.clan.ClanWar;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

public class RequestStartPledgeWar implements IClientIncomingPacket
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
		
		final Clan clanDeclaringWar = player.getClan();
		if (clanDeclaringWar == null)
		{
			return;
		}
		
		if ((clanDeclaringWar.getLevel() < 5) || (clanDeclaringWar.getMembersCount() < Config.ALT_CLAN_MEMBERS_FOR_WAR))
		{
			client.sendPacket(new SystemMessage(SystemMessageId.A_CLAN_WAR_CAN_ONLY_BE_DECLARED_IF_THE_CLAN_IS_LV_5_OR_HIGHER_AND_THE_NUMBER_OF_CLAN_MEMBERS_IS_15_OR_GREATER));
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if (!player.hasClanPrivilege(ClanPrivilege.CL_PLEDGE_WAR))
		{
			client.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if (clanDeclaringWar.getWarCount() >= 30)
		{
			client.sendPacket(SystemMessageId.A_DECLARATION_OF_WAR_AGAINST_MORE_THAN_30_CLANS_CAN_T_BE_MADE_AT_THE_SAME_TIME);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final Clan clanDeclaredWar = ClanTable.getInstance().getClanByName(_pledgeName);
		if (clanDeclaredWar == null)
		{
			client.sendPacket(new SystemMessage(SystemMessageId.A_CLAN_WAR_CANNOT_BE_DECLARED_AGAINST_A_CLAN_THAT_DOES_NOT_EXIST));
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if (clanDeclaredWar == clanDeclaringWar)
		{
			client.sendPacket(new SystemMessage(SystemMessageId.FOOL_YOU_CANNOT_DECLARE_WAR_AGAINST_YOUR_OWN_CLAN));
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if ((clanDeclaringWar.getAllyId() == clanDeclaredWar.getAllyId()) && (clanDeclaringWar.getAllyId() != 0))
		{
			client.sendPacket(new SystemMessage(SystemMessageId.A_DECLARATION_OF_CLAN_WAR_AGAINST_AN_ALLIED_CLAN_CAN_T_BE_MADE));
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if ((clanDeclaredWar.getLevel() < 5) || (clanDeclaredWar.getMembersCount() < Config.ALT_CLAN_MEMBERS_FOR_WAR))
		{
			client.sendPacket(new SystemMessage(SystemMessageId.A_CLAN_WAR_CAN_ONLY_BE_DECLARED_IF_THE_CLAN_IS_LV_5_OR_HIGHER_AND_THE_NUMBER_OF_CLAN_MEMBERS_IS_15_OR_GREATER));
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if (clanDeclaredWar.getDissolvingExpiryTime() > Chronos.currentTimeMillis())
		{
			client.sendPacket(new SystemMessage(SystemMessageId.A_CLAN_WAR_CAN_NOT_BE_DECLARED_AGAINST_A_CLAN_THAT_IS_BEING_DISSOLVED));
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final ClanWar clanWar = clanDeclaringWar.getWarWith(clanDeclaredWar.getId());
		if (clanWar != null)
		{
			if (clanWar.getClanWarState(clanDeclaringWar) == ClanWarState.WIN)
			{
				final SystemMessage sm = new SystemMessage(SystemMessageId.YOU_CAN_T_DECLARE_A_WAR_BECAUSE_THE_21_DAY_PERIOD_HASN_T_PASSED_AFTER_A_DEFEAT_DECLARATION_WITH_THE_S1_CLAN);
				sm.addString(clanDeclaredWar.getName());
				client.sendPacket(sm);
				client.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			
			final SystemMessage sm = new SystemMessage(SystemMessageId.YOU_HAVE_ALREADY_BEEN_AT_WAR_WITH_THE_S1_CLAN_5_DAYS_MUST_PASS_BEFORE_YOU_CAN_DECLARE_WAR_AGAIN);
			sm.addString(clanDeclaredWar.getName());
			client.sendPacket(sm);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final ClanWar newClanWar = new ClanWar(clanDeclaringWar, clanDeclaredWar);
		ClanTable.getInstance().storeClanWars(newClanWar);
		
		for (ClanMember member : clanDeclaringWar.getMembers())
		{
			if ((member != null) && member.isOnline())
			{
				member.getPlayerInstance().broadcastUserInfo(UserInfoType.CLAN);
			}
		}
		for (ClanMember member : clanDeclaredWar.getMembers())
		{
			if ((member != null) && member.isOnline())
			{
				member.getPlayerInstance().broadcastUserInfo(UserInfoType.CLAN);
			}
		}
	}
}
