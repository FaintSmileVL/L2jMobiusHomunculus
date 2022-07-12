package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanMember;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.PledgeReceiveMemberInfo;

/**
 * Format: (ch) dS
 * @author -Wooden-
 */
public class RequestPledgeMemberInfo implements IClientIncomingPacket
{
	@SuppressWarnings("unused")
	private int _unk1;
	private String _player;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_unk1 = packet.readD();
		_player = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		// LOGGER.info("C5: RequestPledgeMemberInfo d:"+_unk1);
		// LOGGER.info("C5: RequestPledgeMemberInfo S:"+_player);
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		// do we need powers to do that??
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return;
		}
		
		final ClanMember member = clan.getClanMember(_player);
		if (member == null)
		{
			return;
		}
		client.sendPacket(new PledgeReceiveMemberInfo(member));
	}
}
