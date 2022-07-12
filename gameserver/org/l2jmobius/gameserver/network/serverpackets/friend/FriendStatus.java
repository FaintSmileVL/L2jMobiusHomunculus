package org.l2jmobius.gameserver.network.serverpackets.friend;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * Support for "Chat with Friends" dialog. <br />
 * Inform player about friend online status change
 * @author JIV
 */
public class FriendStatus implements IClientOutgoingPacket
{
	public static final int MODE_OFFLINE = 0;
	public static final int MODE_ONLINE = 1;
	public static final int MODE_LEVEL = 2;
	public static final int MODE_CLASS = 3;
	
	private final int _type;
	private final int _objectId;
	private final int _classId;
	private final int _level;
	private final String _name;
	
	public FriendStatus(PlayerInstance player, int type)
	{
		_objectId = player.getObjectId();
		_classId = player.getActiveClass();
		_level = player.getLevel();
		_name = player.getName();
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.FRIEND_STATUS.writeId(packet);
		
		packet.writeD(_type);
		packet.writeS(_name);
		switch (_type)
		{
			case MODE_OFFLINE:
			{
				packet.writeD(_objectId);
				break;
			}
			case MODE_LEVEL:
			{
				packet.writeD(_level);
				break;
			}
			case MODE_CLASS:
			{
				packet.writeD(_classId);
				break;
			}
		}
		return true;
	}
}
