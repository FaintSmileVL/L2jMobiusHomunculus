package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import util.CommonUtil;
import org.l2jmobius.gameserver.instancemanager.ClanEntryManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExPledgeDraftListSearch;

/**
 * @author Sdw
 */
public class RequestPledgeDraftListSearch implements IClientIncomingPacket
{
	private int _levelMin;
	private int _levelMax;
	private int _classId;
	private String _query;
	private int _sortBy;
	private boolean _descending;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_levelMin = CommonUtil.constrain(packet.readD(), 0, 107);
		_levelMax = CommonUtil.constrain(packet.readD(), 0, 107);
		_classId = packet.readD();
		_query = packet.readS();
		_sortBy = packet.readD();
		_descending = packet.readD() == 2;
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
			client.sendPacket(new ExPledgeDraftListSearch(ClanEntryManager.getInstance().getSortedWaitingList(_levelMin, _levelMax, _classId, _sortBy, _descending)));
		}
		else
		{
			client.sendPacket(new ExPledgeDraftListSearch(ClanEntryManager.getInstance().queryWaitingListByName(_query.toLowerCase())));
		}
	}
}
