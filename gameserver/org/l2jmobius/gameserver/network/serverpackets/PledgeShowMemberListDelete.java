package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgeShowMemberListDelete implements IClientOutgoingPacket
{
	private final String _player;
	
	public PledgeShowMemberListDelete(String playerName)
	{
		_player = playerName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SHOW_MEMBER_LIST_DELETE.writeId(packet);
		
		packet.writeS(_player);
		return true;
	}
}
