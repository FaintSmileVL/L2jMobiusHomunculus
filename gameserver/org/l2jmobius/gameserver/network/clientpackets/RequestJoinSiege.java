package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * @author KenM
 */
public class RequestJoinSiege implements IClientIncomingPacket
{
	private int _castleId;
	private int _isAttacker;
	private int _isJoining;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_castleId = packet.readD();
		_isAttacker = packet.readD();
		_isJoining = packet.readD();
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
		
		if (!player.hasClanPrivilege(ClanPrivilege.CS_MANAGE_SIEGE))
		{
			client.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return;
		}
		
		final Castle castle = CastleManager.getInstance().getCastleById(_castleId);
		if (castle != null)
		{
			if (_isJoining == 1)
			{
				if (Chronos.currentTimeMillis() < clan.getDissolvingExpiryTime())
				{
					client.sendPacket(SystemMessageId.YOUR_CLAN_MAY_NOT_REGISTER_TO_PARTICIPATE_IN_A_SIEGE_WHILE_UNDER_A_GRACE_PERIOD_OF_THE_CLAN_S_DISSOLUTION);
					return;
				}
				if (_isAttacker == 1)
				{
					castle.getSiege().registerAttacker(player);
				}
				else
				{
					castle.getSiege().registerDefender(player);
				}
			}
			else
			{
				castle.getSiege().removeSiegeClan(player);
			}
			castle.getSiege().listRegisterClan(player);
		}
	}
}
