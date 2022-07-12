package org.l2jmobius.gameserver.network.serverpackets.friend;

import java.util.Calendar;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExFriendDetailInfo implements IClientOutgoingPacket
{
	private final int _objectId;
	private final PlayerInstance _friend;
	private final String _name;
	private final int _lastAccess;
	
	public ExFriendDetailInfo(PlayerInstance player, String name)
	{
		_objectId = player.getObjectId();
		_name = name;
		_friend = World.getInstance().getPlayer(_name);
		_lastAccess = (_friend == null) || _friend.isBlocked(player) ? 0 : _friend.isOnline() ? (int) Chronos.currentTimeMillis() : (int) (Chronos.currentTimeMillis() - _friend.getLastAccess()) / 1000;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_FRIEND_DETAIL_INFO.writeId(packet);
		
		packet.writeD(_objectId);
		
		if (_friend == null)
		{
			packet.writeS(_name);
			packet.writeD(0);
			packet.writeD(0);
			packet.writeH(0);
			packet.writeH(0);
			packet.writeD(0);
			packet.writeD(0);
			packet.writeS("");
			packet.writeD(0);
			packet.writeD(0);
			packet.writeS("");
			packet.writeD(1);
			packet.writeS(""); // memo
		}
		else
		{
			packet.writeS(_friend.getName());
			packet.writeD(_friend.isOnlineInt());
			packet.writeD(_friend.getObjectId());
			packet.writeH(_friend.getLevel());
			packet.writeH(_friend.getClassId().getId());
			packet.writeD(_friend.getClanId());
			packet.writeD(_friend.getClanCrestId());
			packet.writeS(_friend.getClan() != null ? _friend.getClan().getName() : "");
			packet.writeD(_friend.getAllyId());
			packet.writeD(_friend.getAllyCrestId());
			packet.writeS(_friend.getClan() != null ? _friend.getClan().getAllyName() : "");
			final Calendar createDate = _friend.getCreateDate();
			packet.writeC(createDate.get(Calendar.MONTH) + 1);
			packet.writeC(createDate.get(Calendar.DAY_OF_MONTH));
			packet.writeD(_lastAccess);
			packet.writeS(""); // memo
		}
		return true;
	}
}
