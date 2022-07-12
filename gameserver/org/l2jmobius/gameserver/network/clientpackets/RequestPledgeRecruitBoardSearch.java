package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.ClanEntryManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExPledgeRecruitBoardSearch;

/**
 * @author Sdw
 */
public class RequestPledgeRecruitBoardSearch implements IClientIncomingPacket
{
	private int _clanLevel;
	private int _karma;
	private int _type;
	private String _query;
	private int _sort;
	private boolean _descending;
	private int _page;
	@SuppressWarnings("unused")
	private int _applicationType;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_clanLevel = packet.readD();
		_karma = packet.readD();
		_type = packet.readD();
		_query = packet.readS();
		_sort = packet.readD();
		_descending = packet.readD() == 2;
		_page = packet.readD();
		_applicationType = packet.readD(); // Helios
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
		
		if (_query.isEmpty())
		{
			if ((_karma < 0) && (_clanLevel < 0))
			{
				player.sendPacket(new ExPledgeRecruitBoardSearch(ClanEntryManager.getInstance().getUnSortedClanList(), _page));
			}
			else
			{
				player.sendPacket(new ExPledgeRecruitBoardSearch(ClanEntryManager.getInstance().getSortedClanList(_clanLevel, _karma, _sort, _descending), _page));
			}
		}
		else
		{
			player.sendPacket(new ExPledgeRecruitBoardSearch(ClanEntryManager.getInstance().getSortedClanListByName(_query.toLowerCase(), _type), _page));
		}
	}
}
