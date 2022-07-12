package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ManagePledgePower;

public class RequestPledgePower implements IClientIncomingPacket
{
	private int _rank;
	private int _action;
	private int _privs;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_rank = packet.readD();
		_action = packet.readD();
		if (_action == 2)
		{
			_privs = packet.readD();
		}
		else
		{
			_privs = 0;
		}
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
		
		player.sendPacket(new ManagePledgePower(client.getPlayer().getClan(), _action, _rank));
		if ((_action == 2) && player.isClanLeader())
		{
			if (_rank == 9)
			{
				// The rights below cannot be bestowed upon Academy members:
				// Join a clan or be dismissed
				// Title management, crest management, master management, level management,
				// bulletin board administration
				// Clan war, right to dismiss, set functions
				// Auction, manage taxes, attack/defend registration, mercenary management
				// => Leaves only CP_CL_VIEW_WAREHOUSE, CP_CH_OPEN_DOOR, CP_CS_OPEN_DOOR?
				_privs &= ClanPrivilege.CL_VIEW_WAREHOUSE.getBitmask() | ClanPrivilege.CH_OPEN_DOOR.getBitmask() | ClanPrivilege.CS_OPEN_DOOR.getBitmask();
			}
			player.getClan().setRankPrivs(_rank, _privs);
		}
	}
}