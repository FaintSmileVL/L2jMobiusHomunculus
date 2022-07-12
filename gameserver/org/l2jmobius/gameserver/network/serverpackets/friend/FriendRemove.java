package org.l2jmobius.gameserver.network.serverpackets.friend;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class FriendRemove implements IClientOutgoingPacket
{
	private final int _responce;
	private final String _charName;
	
	public FriendRemove(String charName, int responce)
	{
		_responce = responce;
		_charName = charName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.FRIEND_REMOVE.writeId(packet);
		
		packet.writeD(_responce);
		packet.writeS(_charName);
		return true;
	}
}
