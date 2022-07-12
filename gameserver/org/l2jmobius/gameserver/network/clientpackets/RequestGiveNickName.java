package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

public class RequestGiveNickName implements IClientIncomingPacket
{
	private String _target;
	private String _title;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_target = packet.readS();
		_title = packet.readS();
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
		
		// Noblesse can bestow a title to themselves
		if ((player.getNobleLevel() > 0) && _target.equalsIgnoreCase(player.getName()))
		{
			player.setTitle(_title);
			client.sendPacket(SystemMessageId.YOUR_TITLE_HAS_BEEN_CHANGED);
			player.broadcastTitleInfo();
		}
		else
		{
			// Can the player change/give a title?
			if (!player.hasClanPrivilege(ClanPrivilege.CL_GIVE_TITLE))
			{
				client.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
				return;
			}
			
			if (player.getClan().getLevel() < 3)
			{
				client.sendPacket(SystemMessageId.THE_CLAN_MUST_BE_LEVEL_3_OR_ABOVE_TO_GRANT_A_TITLE);
				return;
			}
			
			final ClanMember member1 = player.getClan().getClanMember(_target);
			if (member1 != null)
			{
				final PlayerInstance member = member1.getPlayerInstance();
				if (member != null)
				{
					// is target from the same clan?
					member.setTitle(_title);
					member.sendPacket(SystemMessageId.YOUR_TITLE_HAS_BEEN_CHANGED);
					member.broadcastTitleInfo();
				}
				else
				{
					client.sendPacket(SystemMessageId.THAT_PLAYER_IS_NOT_ONLINE);
				}
			}
			else
			{
				client.sendPacket(SystemMessageId.THE_TARGET_MUST_BE_A_CLAN_MEMBER);
			}
		}
	}
}
