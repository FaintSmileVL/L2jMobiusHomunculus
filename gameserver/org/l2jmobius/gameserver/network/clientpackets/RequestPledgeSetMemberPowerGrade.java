package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Format: (ch) Sd
 * @author -Wooden-
 */
public class RequestPledgeSetMemberPowerGrade implements IClientIncomingPacket
{
	private String _member;
	private int _powerGrade;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_member = packet.readS();
		_powerGrade = packet.readD();
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
		
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return;
		}
		
		if (!player.hasClanPrivilege(ClanPrivilege.CL_MANAGE_RANKS))
		{
			return;
		}
		
		final ClanMember member = clan.getClanMember(_member);
		if (member == null)
		{
			return;
		}
		
		if (member.getObjectId() == clan.getLeaderId())
		{
			return;
		}
		
		if (member.getPledgeType() == -1) // Academy - Removed.
		{
			// also checked from client side
			player.sendPacket(SystemMessageId.THAT_PRIVILEGE_CANNOT_BE_GRANTED_TO_A_CLAN_ACADEMY_MEMBER);
			return;
		}
		
		member.setPowerGrade(_powerGrade);
		clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(member));
		clan.broadcastToOnlineMembers(new SystemMessage(SystemMessageId.CLAN_MEMBER_C1_S_PRIVILEGE_LEVEL_HAS_BEEN_CHANGED_TO_S2).addString(member.getName()).addInt(_powerGrade));
	}
}
