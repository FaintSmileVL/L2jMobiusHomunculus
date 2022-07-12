package org.l2jmobius.gameserver.network.clientpackets;

import java.util.LinkedList;
import java.util.List;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.ClassId;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExListPartyMatchingWaitingRoom;

/**
 * @author Gnacik
 */
public class RequestListPartyMatchingWaitingRoom implements IClientIncomingPacket
{
	private int _page;
	private int _minLevel;
	private int _maxLevel;
	private List<ClassId> _classId; // 1 - waitlist 0 - room waitlist
	private String _query;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_page = packet.readD();
		_minLevel = packet.readD();
		_maxLevel = packet.readD();
		final int size = packet.readD();
		if ((size > 0) && (size < 128))
		{
			_classId = new LinkedList<>();
			for (int i = 0; i < size; i++)
			{
				_classId.add(ClassId.getClassId(packet.readD()));
			}
		}
		if (packet.getReadableBytes() > 0)
		{
			_query = packet.readS();
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
		
		client.sendPacket(new ExListPartyMatchingWaitingRoom(_page, _minLevel, _maxLevel, _classId, _query));
	}
}