package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.MatchingRoomType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.CommandChannelMatchingRoom;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExMPCCRoomInfo;

/**
 * @author Sdw
 */
public class RequestExManageMpccRoom implements IClientIncomingPacket
{
	private int _roomId;
	private int _maxMembers;
	private int _minLevel;
	private int _maxLevel;
	private String _title;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_roomId = packet.readD();
		_maxMembers = packet.readD();
		_minLevel = packet.readD();
		_maxLevel = packet.readD();
		packet.readD(); // Party Distrubtion Type
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
		
		final MatchingRoom room = player.getMatchingRoom();
		if ((room == null) || (room.getId() != _roomId) || (room.getRoomType() != MatchingRoomType.COMMAND_CHANNEL) || (room.getLeader() != player))
		{
			return;
		}
		
		room.setTitle(_title);
		room.setMaxMembers(_maxMembers);
		room.setMinLevel(_minLevel);
		room.setMaxLevel(_maxLevel);
		
		room.getMembers().forEach(p -> p.sendPacket(new ExMPCCRoomInfo((CommandChannelMatchingRoom) room)));
		player.sendPacket(SystemMessageId.THE_COMMAND_CHANNEL_MATCHING_ROOM_INFORMATION_WAS_EDITED);
	}
}
