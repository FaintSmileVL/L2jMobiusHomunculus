package org.l2jmobius.gameserver.network.serverpackets.friend;

import java.util.LinkedList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.CharNameTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * Support for "Chat with Friends" dialog. <br />
 * This packet is sent only at login.
 * @author Tempy
 */
public class L2FriendList implements IClientOutgoingPacket
{
	private final List<FriendInfo> _info = new LinkedList<>();
	
	private static class FriendInfo
	{
		int _objId;
		String _name;
		int _level;
		int _classId;
		boolean _online;
		
		public FriendInfo(int objId, String name, boolean online, int level, int classId)
		{
			_objId = objId;
			_name = name;
			_online = online;
			_level = level;
			_classId = classId;
		}
	}
	
	public L2FriendList(PlayerInstance player)
	{
		for (int objId : player.getFriendList())
		{
			final String name = CharNameTable.getInstance().getNameById(objId);
			final PlayerInstance player1 = World.getInstance().getPlayer(objId);
			boolean online = false;
			int level = 0;
			int classId = 0;
			if (player1 != null)
			{
				online = true;
				level = player1.getLevel();
				classId = player1.getClassId().getId();
			}
			else
			{
				level = CharNameTable.getInstance().getLevelById(objId);
				classId = CharNameTable.getInstance().getClassIdById(objId);
			}
			_info.add(new FriendInfo(objId, name, online, level, classId));
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.L2_FRIEND_LIST.writeId(packet);
		
		packet.writeD(_info.size());
		for (FriendInfo info : _info)
		{
			packet.writeD(info._objId); // character id
			packet.writeS(info._name);
			packet.writeD(info._online ? 0x01 : 0x00); // online
			packet.writeD(info._online ? info._objId : 0x00); // object id if online
			packet.writeD(info._level);
			packet.writeD(info._classId);
			packet.writeH(0x00);
		}
		return true;
	}
}