package org.l2jmobius.gameserver.network.serverpackets.friend;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class FriendAddRequestResult implements IClientOutgoingPacket
{
	private final int _result;
	private final int _charId;
	private final String _charName;
	private final int _isOnline;
	private final int _charObjectId;
	private final int _charLevel;
	private final int _charClassId;
	
	public FriendAddRequestResult(PlayerInstance player, int result)
	{
		_result = result;
		_charId = player.getObjectId();
		_charName = player.getName();
		_isOnline = player.isOnlineInt();
		_charObjectId = player.getObjectId();
		_charLevel = player.getLevel();
		_charClassId = player.getActiveClass();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.FRIEND_ADD_REQUEST_RESULT.writeId(packet);
		
		packet.writeD(_result);
		packet.writeD(_charId);
		packet.writeS(_charName);
		packet.writeD(_isOnline);
		packet.writeD(_charObjectId);
		packet.writeD(_charLevel);
		packet.writeD(_charClassId);
		packet.writeH(0x00); // Always 0 on retail
		return true;
	}
}
