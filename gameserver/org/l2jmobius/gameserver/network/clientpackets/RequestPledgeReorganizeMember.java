package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.ClanLevelData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Format: (ch) dSdS
 * @author -Wooden-
 */
public class RequestPledgeReorganizeMember implements IClientIncomingPacket
{
	private String _memberName;
	private int _newPledgeType;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readD(); // _isMemberSelected
		_memberName = packet.readS();
		_newPledgeType = packet.readD();
		packet.readS(); // _selectedMember
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
		
		final ClanMember member1 = clan.getClanMember(_memberName);
		if ((member1 == null) || (member1.getObjectId() == clan.getLeaderId()))
		{
			return;
		}
		
		final int oldPledgeType = member1.getPledgeType();
		if (oldPledgeType == _newPledgeType)
		{
			return;
		}
		
		if (clan.getSubPledgeMembersCount(_newPledgeType) >= (_newPledgeType == 0 ? ClanLevelData.getCommonMemberLimit(clan.getLevel()) : ClanLevelData.getEliteMemberLimit(clan.getLevel())))
		{
			final SystemMessage sm = new SystemMessage(SystemMessageId.S1_IS_FULL_AND_CANNOT_ACCEPT_ADDITIONAL_CLAN_MEMBERS_AT_THIS_TIME);
			sm.addString(_newPledgeType == 0 ? "Common Members" : "Elite Members");
			player.sendPacket(sm);
			return;
		}
		
		member1.setPledgeType(_newPledgeType);
		clan.broadcastClanStatus();
	}
}
