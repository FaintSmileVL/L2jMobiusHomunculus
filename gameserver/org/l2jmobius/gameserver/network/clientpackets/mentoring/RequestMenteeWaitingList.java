package org.l2jmobius.gameserver.network.clientpackets.mentoring;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.mentoring.ListMenteeWaiting;

/**
 * @author UnAfraid
 */
public class RequestMenteeWaitingList implements IClientIncomingPacket
{
	private int _page;
	private int _minLevel;
	private int _maxLevel;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_page = packet.readD();
		_minLevel = packet.readD();
		_maxLevel = packet.readD();
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
		client.sendPacket(new ListMenteeWaiting(_page, _minLevel, _maxLevel));
	}
}
