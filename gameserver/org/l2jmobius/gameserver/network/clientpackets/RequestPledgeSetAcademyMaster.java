package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Format: (ch) dSS
 * @author -Wooden-
 */
public class RequestPledgeSetAcademyMaster implements IClientIncomingPacket
{
	private String _currPlayerName;
	private int _set; // 1 set, 0 delete
	private String _targetPlayerName;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_set = packet.readD();
		_currPlayerName = packet.readS();
		_targetPlayerName = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return;
		}
		
		if (!player.hasClanPrivilege(ClanPrivilege.CL_APPRENTICE))
		{
			player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_THE_RIGHT_TO_DISMISS_AN_APPRENTICE);
			return;
		}
		
		final ClanMember currentMember = clan.getClanMember(_currPlayerName);
		final ClanMember targetMember = clan.getClanMember(_targetPlayerName);
		if ((currentMember == null) || (targetMember == null))
		{
			return;
		}
		
		ClanMember apprenticeMember;
		ClanMember sponsorMember;
		if (currentMember.getPledgeType() == -1) // Academy - Removed.
		{
			apprenticeMember = currentMember;
			sponsorMember = targetMember;
		}
		else
		{
			apprenticeMember = targetMember;
			sponsorMember = currentMember;
		}
		
		final PlayerInstance apprentice = apprenticeMember.getPlayerInstance();
		final PlayerInstance sponsor = sponsorMember.getPlayerInstance();
		SystemMessage sm = null;
		if (_set == 0)
		{
			// test: do we get the current sponsor & apprentice from this packet or no?
			if (apprentice != null)
			{
				apprentice.setSponsor(0);
			}
			else
			{
				apprenticeMember.setApprenticeAndSponsor(0, 0);
			}
			
			if (sponsor != null)
			{
				sponsor.setApprentice(0);
			}
			else
			{
				sponsorMember.setApprenticeAndSponsor(0, 0);
			}
			
			apprenticeMember.saveApprenticeAndSponsor(0, 0);
			sponsorMember.saveApprenticeAndSponsor(0, 0);
			sm = new SystemMessage(SystemMessageId.S2_CLAN_MEMBER_C1_S_APPRENTICE_HAS_BEEN_REMOVED);
		}
		else
		{
			if ((apprenticeMember.getSponsor() != 0) || (sponsorMember.getApprentice() != 0) || (apprenticeMember.getApprentice() != 0) || (sponsorMember.getSponsor() != 0))
			{
				// TODO retail message
				player.sendMessage("Remove previous connections first.");
				return;
			}
			if (apprentice != null)
			{
				apprentice.setSponsor(sponsorMember.getObjectId());
			}
			else
			{
				apprenticeMember.setApprenticeAndSponsor(0, sponsorMember.getObjectId());
			}
			
			if (sponsor != null)
			{
				sponsor.setApprentice(apprenticeMember.getObjectId());
			}
			else
			{
				sponsorMember.setApprenticeAndSponsor(apprenticeMember.getObjectId(), 0);
			}
			
			// saving to database even if online, since both must match
			apprenticeMember.saveApprenticeAndSponsor(0, sponsorMember.getObjectId());
			sponsorMember.saveApprenticeAndSponsor(apprenticeMember.getObjectId(), 0);
			sm = new SystemMessage(SystemMessageId.S2_HAS_BEEN_DESIGNATED_AS_THE_APPRENTICE_OF_CLAN_MEMBER_S1);
		}
		sm.addString(sponsorMember.getName());
		sm.addString(apprenticeMember.getName());
		if ((sponsor != player) && (sponsor != apprentice))
		{
			player.sendPacket(sm);
		}
		if (sponsor != null)
		{
			sponsor.sendPacket(sm);
		}
		if (apprentice != null)
		{
			apprentice.sendPacket(sm);
		}
	}
}
