package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExAskJoinPartyRoom implements IClientOutgoingPacket
{
	private final String _charName;
	private final String _roomName;
	
	public ExAskJoinPartyRoom(PlayerInstance player)
	{
		_charName = player.getName();
		_roomName = player.getMatchingRoom().getTitle();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ASK_JOIN_PARTY_ROOM.writeId(packet);
		
		packet.writeS(_charName);
		packet.writeS(_roomName);
		return true;
	}
}
